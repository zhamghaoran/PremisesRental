package com.rental.premisesrental.service.impl;

import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.service.LoginService;
import com.rental.premisesrental.util.MD5Util;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 20179
 */
public class LoginServiceImpl implements LoginService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Response login(String username, String password) {
        return null;
    }

    @Override
    public Response register(String username, String password) {
        password = MD5Util.getMd5Encode(password);
        User user = new User(username, password);
        int insert = userMapper.insert(user);
        if (insert > 0) {
            return  Response.success();
        }
        else {
            return Response.fail().setFailMessage("数据库异常");
        }
    }

}
