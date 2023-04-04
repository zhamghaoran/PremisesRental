package com.rental.premisesrental.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rental.premisesrental.entity.Order;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.pojo.OrderDTO;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.AvailableTimeUtil;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import org.redisson.api.RFuture;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.rental.premisesrental.util.constant.LOCK_PLACE_KEY;

/**
 * @author 20179
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PlaceMapper placeMapper;

    @Resource
    private RedissonClient redisClient;

    @Override
    public Response createOrder(Long shopId, Long placeId, Integer dayOffSet, Integer beginTime, Integer rentTime) throws InterruptedException {
        Long userId = UserHolder.getCurrentUser().getId();
        //创建锁对象
        RLock redisLock = redisClient.getLock("lock:order:" + placeId);
        Timestamp beginRentTime = AvailableTimeUtil.getFinalDate(dayOffSet, beginTime);
        Order order = new Order(
                userId,
                shopId,
                placeId,
                beginRentTime,
                rentTime,
                new Timestamp(System.currentTimeMillis())
        );
        // 获取锁
        boolean isLock = redisLock.tryLock(10, TimeUnit.SECONDS);
        if (!isLock) {
            //获取锁失败,返回失败信息
            return Response.fail().setFailMessage("请求超时!");
        }
        LambdaQueryWrapper<Place> placeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        placeLambdaQueryWrapper.eq(Place::getId, placeId);
        Place place = placeMapper.selectOne(placeLambdaQueryWrapper);
        // 获取到所有的列表
        List<Long> longs = JSON.parseArray(place.getAvailable()).toList(Long.class);
        // 取出一个列表

        Long day = longs.get(dayOffSet);
        long tem = 1;
        tem <<= (24 - beginTime);
        for (int i = beginTime; i < beginTime + rentTime; i++) {
            if ((day >> (24 - i) & 1) == 1) {
                redisLock.unlock();
                return Response.fail().setFailMessage("预约时间冲突");
            }
            day |= tem;
            tem >>= 1;
        }
        orderMapper.insert(order);
        longs.set(dayOffSet, day);
        place.setAvailable(JSON.toJSONString(longs));
        placeMapper.update(place, placeLambdaQueryWrapper);
        redisLock.unlock();
        return Response.success();
    }

    @Override
    public Response queryOrder() {
        Long userId = UserHolder.getCurrentUser().getId();
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getUserId, userId);
        List<Order> orders = orderMapper.selectList(orderLambdaQueryWrapper);
        return copy(orders);
    }

    @Override
    public Response deleteOrder(Long orderId) throws InterruptedException {
        Order order = orderMapper.selectById(orderId);
        if (StringUtils.checkValNull(order)) {
            return Response.fail().setFailMessage("订单id错误");
        }
        Place place = placeMapper.selectById(order.getPlaceId());
        Timestamp beginTime = order.getBeginTime();

        if (beginTime.before(new Timestamp(System.currentTimeMillis()))) {
            return Response.fail().setFailMessage("已消费，无法撤销");
        }
        //获取时间，并计算时间差
        Integer between = AvailableTimeUtil.getbetweenDay(beginTime);
        // 修改时间
        List<Long> lists = JSON.parseArray(place.getAvailable()).toList(Long.class);
        Long determinedDays = lists.get(between);
        int beginHour = DateUtil.hour(DateUtil.date(order.getBeginTime().getTime()), true);
        Long finalTime = AvailableTimeUtil.changeTime(determinedDays, beginHour, order.getRentTime(), 0);

        if (finalTime == -1) {
            return Response.fail().setFailMessage("删除失败");
        }
        // 将修改完的时间写回
        lists.set((int) between, finalTime);
        place.setAvailable(JSON.toJSONString(lists));
        boolean b = redisClient.getLock(LOCK_PLACE_KEY + place.getId()).tryLock(10, TimeUnit.SECONDS);
        if (!b) {
            return Response.fail().setFailMessage("超时");
        }
        LambdaQueryWrapper<Place> placeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        placeLambdaQueryWrapper.eq(Place::getId, place.getId());
        placeMapper.update(place, placeLambdaQueryWrapper);
        order.setIsDeleted(1);
        orderMapper.updateById(order);
        redisClient.getLock(LOCK_PLACE_KEY + place.getId()).unlock();
        return Response.success().setSuccessMessage("删除成功");
    }

    private Response copy(List<Order> orders) {
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orders.forEach(i -> {
            OrderDTO orderDTO = BeanUtil.copyProperties(i, OrderDTO.class);
            orderDTO.setCertainTime(i.getBeginTime());
            orderDTO.setId(i.getId().toString());
            orderDTO.setShopId(i.getShopId().toString());
            orderDTO.setUserId(i.getUserId().toString());
            orderDTOS.add(orderDTO);
        });
        return Response.success().setSuccessData(orderDTOS);
    }
}
