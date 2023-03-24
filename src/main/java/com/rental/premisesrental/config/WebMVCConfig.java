package com.rental.premisesrental.config;

import com.rental.premisesrental.filter.LoginCheckFilter;
import com.rental.premisesrental.filter.RefreshTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WebMVCConfig extends WebMvcConfigurationSupport {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //添加登录拦截器
        registry.addInterceptor(new LoginCheckFilter())
                .excludePathPatterns(
                        "/*"
                ).order(1);

        //添加token刷新拦截器
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }
}
