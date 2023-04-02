package com.rental.premisesrental.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rental.premisesrental.entity.Order;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.pojo.OrderDTO;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.AvailableTimeUtil;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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


    @Override
    public Response createOrder(Long shopId, Long placeId, Integer dayOffSet, Integer beginTime, Integer rentTime) {
        Timestamp beginRentTime = AvailableTimeUtil.getFinalDate(dayOffSet, beginTime);
        Order order = new Order(
                UserHolder.getCurrentUser().getId(),
                shopId,
                placeId,
                beginRentTime,
                rentTime,
                new Timestamp(System.currentTimeMillis())
        );
        orderMapper.insert(order);
        LambdaQueryWrapper<Place> placeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        placeLambdaQueryWrapper.eq(Place::getId, placeId);
        Place place = placeMapper.selectOne(placeLambdaQueryWrapper);
        // 获取到所有的列表
        List<Long> longs = JSON.parseArray(place.getAvailable()).toList(Long.class);
         // 取出一个列表
        Long day = longs.get(dayOffSet);

        long tem = 1;
        tem <<= (24 - beginTime);
        for (int i = beginTime;i < beginTime + rentTime;i ++) {
            if ((day >> (24 - i) & 1) == 1) {
                return Response.fail().setFailMessage("预约时间冲突");
            }
            day |= tem;
            tem >>= 1;
        }
//        for (int i = 1; i <= rentTime; i++) {
//            if ((day ^ tem) == 1) {
//                return Response.fail().setFailMessage("预约时间冲突");
//            }
//            day |= tem;
//            tem >>= 1;
//        }
        orderMapper.insert(order);
        longs.set(dayOffSet, day);
        place.setAvailable(JSON.toJSONString(longs));
        placeMapper.update(place, placeLambdaQueryWrapper);
        return Response.success();
    }

    @Override
    public Response queryOrder() {
        Long userId = UserHolder.getCurrentUser().getId();
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getUserId, userId);
        List<Order> orders = orderMapper.selectList(orderLambdaQueryWrapper);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        orders.forEach(i -> {
            OrderDTO orderDTO = BeanUtil.copyProperties(i, OrderDTO.class);
            orderDTO.setCertainTime(i.getBeginTime());
            orderDTOS.add(orderDTO);
        });
        return Response.success().setSuccessData(orderDTOS);
    }

    @Override
    public Response deleteOrder(Long orderId) {
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
        Long determinedDays = lists.get((int) between);
        int beginHour = DateUtil.hour(DateUtil.date(order.getBeginTime().getTime()), true);
        Long finalTime = AvailableTimeUtil.changeTime(determinedDays, beginHour, order.getRentTime(), 0);
        if (finalTime == 1) {
            return Response.fail().setFailMessage("删除失败");
        }

        // 将修改完的时间写回
        lists.set((int) between, finalTime);
        place.setAvailable(JSON.toJSONString(lists));
        LambdaQueryWrapper<Place> placeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        placeLambdaQueryWrapper.eq(Place::getId, place.getId());
        placeMapper.update(place, placeLambdaQueryWrapper);
        order.setIsDeleted(1);
        orderMapper.updateById(order);
        return Response.success().setSuccessMessage("删除成功");
    }
}
