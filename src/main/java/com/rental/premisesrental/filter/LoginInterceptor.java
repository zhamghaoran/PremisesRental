package com.rental.premisesrental.filter;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.rental.premisesrental.util.constant.USER_TOKEN;

public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate redisTemplate;

    public LoginInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public LoginInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        String key = USER_TOKEN + token;
        if (token == null) {
            response.setStatus(401);
            return false;
        }
        String userJSON = redisTemplate.opsForValue().get(key);
        if (userJSON == null || userJSON.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        if (StringUtils.isNotBlank(userJSON)) {
            response.setStatus(401);
            return false;
        }
        User user = JSON.parseObject(userJSON, User.class);

        UserHolder.put(user);
        //设置时间
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
