package com.rental.premisesrental.filter;

import com.rental.premisesrental.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Stack;

public class LoginCheckFilter implements HandlerInterceptor {
    public LoginCheckFilter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public LoginCheckFilter() {
    }

    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否需要拦截
        if (UserHolder.getCurrentUser()==null){
            //没有用户,需要拦截
            response.setStatus(401);
            return false;
        }
        //否则放行
        return true;
    }
}