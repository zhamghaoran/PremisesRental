package com.rental.premisesrental.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMutualExclusionLock {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public boolean getLock() {
        return false;
    }
    public boolean unLock() {
        return false;
    }
}
