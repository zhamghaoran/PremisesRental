package com.rental.premisesrental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.pojo.LoginParam;
import com.rental.premisesrental.util.Response;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 20179
 */
public interface UserService  {
    Response Login(LoginParam loginParam);

    Response sendMsg(String phone);

    Response register(LoginParam loginParam);
}
