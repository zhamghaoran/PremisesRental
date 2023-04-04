package com.rental.premisesrental.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rental.premisesrental.entity.Order;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.pojo.PlaceDTO;
import com.rental.premisesrental.service.PlaceService;
import com.rental.premisesrental.util.AvailableTimeUtil;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.SportsType;
import com.rental.premisesrental.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 20179
 */
@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceMapper placeMapper;

    @Autowired
    private OrderMapper orderMapper;


    private static final List<Long> AVAIABLELIST = new ArrayList<>();
    static {
        for (int i = 1;i <= 7;i ++) {
            AVAIABLELIST.add(0L);
        }
    }

    @Override
    public Response addPlace(Place place) {
        if (!checkSportsType(place.getType())) {
            return Response.fail().setFailMessage("运动类型错误");
        }

        place.setAvailable(JSON.toJSONString(AVAIABLELIST));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        place.setCreateTime(timestamp);
        place.setUpdateTime(timestamp);

        int insert = placeMapper.insert(place);
        if (insert > 0) {
            return Response.success().setSuccessMessage("添加商铺成功");
        } else {
            return Response.fail().setFailMessage("添加商铺失败");
        }
    }

    @Override
    public Response queryPlaceByShopId(Long id) {
        LambdaQueryWrapper<Place> placeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        placeLambdaQueryWrapper.eq(Place::getShopId, id);
        List<Place> places = placeMapper.selectList(placeLambdaQueryWrapper);
        List<PlaceDTO> placeDTOS = new ArrayList<>();
        places.forEach(i -> {
            PlaceDTO placeDTO = BeanUtil.copyProperties(i, PlaceDTO.class);
            placeDTO.setAvailable(AvailableTimeUtil.decimaltoList(i.getAvailable()));
            placeDTO.setId(i.getId().toString());
            placeDTOS.add(placeDTO);
        });
        return Response.success().setSuccessData(placeDTOS);
    }




    private Boolean checkSportsType(String type) {
        return SportsType.checkSportsType(type);
    }
}
