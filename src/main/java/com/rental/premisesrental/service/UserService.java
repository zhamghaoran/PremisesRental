package com.rental.premisesrental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.pojo.LoginParam;
import com.rental.premisesrental.util.Response;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService extends IService<User> {
    public Response Login(LoginParam loginParam);
    public Response sendMsg(String phone);
}
