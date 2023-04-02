package com.rental.premisesrental.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.ScheduledTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Component
@Service
public class ScheduledTasksImpl implements ScheduledTasks {
    @Autowired
    private PlaceMapper placeMapper;

    @Scheduled(cron = "0 00 00 * * ?")
    @Override
    public void databaseTask() {
        List<Place> places = placeMapper.selectList(null);
        places.forEach(this::processJSONTime);
        places.forEach(i -> placeMapper.updateById(i));
    }
    private void processJSONTime(Place place) {
        List<Long> javaList = JSON.parseArray(place.getAvailable()).toJavaList(Long.class);
        javaList.remove(0);
        javaList.add(0L);
        place.setAvailable(JSON.toJSONString(javaList));
    }
}
