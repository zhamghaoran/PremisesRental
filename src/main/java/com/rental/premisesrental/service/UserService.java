package com.rental.premisesrental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.util.Response;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends IService<User> {
    Response Login(Map map, HttpSession session);
}
