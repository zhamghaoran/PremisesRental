package com.rental.premisesrental.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.config.RedissonConfig;
import com.rental.premisesrental.entity.Order;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.AvailableTimeUtil;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import io.lettuce.core.RedisClient;
import lombok.extern.java.Log;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 20179
 */
@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper,Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PlaceMapper placeMapper;
    @Resource
    private RedissonClient redisClient;


    @Override
    public Response createOrder(Long shopId, Long placeId, Integer dayOffSet, Integer beginTime, Integer rentTime) {
        Long userId = UserHolder.getCurrentUser().getId();

        //创建锁对象
        RLock redisLock = redisClient.getLock("lock:order:" + userId);
        //尝试获取锁
        boolean isLock = redisLock.tryLock();
        if (!isLock){
            //获取锁失败,返回失败信息
            return Response.fail().setFailMessage("不允许重复下单!");
        }


        Timestamp beginRentTime = AvailableTimeUtil.getFinalDate(dayOffSet);
        Order order = new Order(
                userId,
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
        tem <<= (24 - beginTime - 1);
        for (int i = 1;i <= rentTime;i ++) {
            if ((day&tem)==0){
                day |= tem;
                tem >>= 1;
                continue;
            }
            return Response.fail().setFailMessage("预约时间冲突");
        }



        longs.set(dayOffSet,day);
        place.setAvailable(JSON.toJSONString(longs));
        placeMapper.update(place,placeLambdaQueryWrapper);
        return Response.success();
    }
}
