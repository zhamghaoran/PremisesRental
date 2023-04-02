package com.rental.premisesrental.controller;

import com.rental.premisesrental.pojo.LoginParam;
import com.rental.premisesrental.service.UserService;
import com.rental.premisesrental.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20179
 */
@RestController
@Slf4j
@Api(value = "用户注册登录")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录通过验证码")
    @PostMapping("/login/code")
    public Response loginByPhone(
            @ApiParam(value = "登录参数")
            @RequestBody LoginParam loginParam
    ) {
        return userService.Login(loginParam);
    }

    @ApiOperation(value = "登录通过密码")
    @PostMapping("/login/password")
    public Response loginByPassword(
            @ApiParam(value = "登录参数")
            @RequestBody
            LoginParam loginParam) {
        return userService.LoginByPassword(loginParam);
    }

    @ApiOperation(value = "发送验证码")
    @GetMapping("/sendMsg")
    public Response sendMsg(
            @ApiParam(value = "手机号")
            String phone) {
        return userService.sendMsg(phone);

    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public Response register(
            @ApiParam(value = "登录参数")
            @RequestBody LoginParam loginParam
    ) {
        return userService.register(loginParam);

    }
    @ApiOperation(value = "忘记密码")
    @PostMapping("/forget/password")
    public Response forgetPassword(
            @ApiParam(value = "参数")
            @RequestBody
            LoginParam loginParam
    ) {
        return userService.forgetPassword(loginParam);
    }
}
