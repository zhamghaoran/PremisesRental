package com.rental.premisesrental.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rental.premisesrental.entity.Order;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.AvailableTimeUtil;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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
        Timestamp beginRentTime = AvailableTimeUtil.getFinalDate(dayOffSet);
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
