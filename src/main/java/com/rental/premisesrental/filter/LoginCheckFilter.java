package com.rental.premisesrental.filter;

import com.alibaba.fastjson2.JSON;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rental.premisesrental.util.constant.USER_TOKEN;

public class LoginCheckFilter implements HandlerInterceptor {
    public LoginCheckFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public LoginCheckFilter() {
    }

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = request.getHeader("token");
        String userJSON = stringRedisTemplate.opsForValue().get(USER_TOKEN + token);
        User user = JSON.parseObject(userJSON, User.class);

        //1.判断是否需要拦截
        if (user==null||UserHolder.getCurrentUser().equals(user)){
            //没有用户,需要拦截
            response.setStatus(401);
            return false;
        }
        //否则放行,并将这个用户存放到ThreadLocal中
        UserHolder.put(user);
        return true;
    }
}