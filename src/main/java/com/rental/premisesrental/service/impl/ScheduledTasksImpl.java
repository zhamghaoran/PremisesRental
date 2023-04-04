package com.rental.premisesrental.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.ScheduledTasks;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.rental.premisesrental.util.constant.LOCK_PLACE_KEY;


@Component
@Service
public class ScheduledTasksImpl implements ScheduledTasks {
    @Autowired
    private PlaceMapper placeMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Scheduled(cron = "0 00 00 * * ?")
    @Override
    public void databaseTask() {
        List<Place> places = placeMapper.selectList(null);
        places.forEach(i -> {
            RLock lock = redissonClient.getLock(LOCK_PLACE_KEY + i.getId());
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            processJSONTime(i);
            placeMapper.updateById(i);
        });

    }
    private void processJSONTime(Place place) {
        List<Long> javaList = JSON.parseArray(place.getAvailable()).toJavaList(Long.class);
        javaList.remove(0);
        javaList.add(0L);
        place.setAvailable(JSON.toJSONString(javaList));
    }
}
