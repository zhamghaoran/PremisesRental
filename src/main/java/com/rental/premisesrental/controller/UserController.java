package com.rental.premisesrental.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.service.LoginService;
import com.rental.premisesrental.service.UserService;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.utils.SMSUtils;
import com.rental.premisesrental.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 20179
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody Map map, HttpSession session) {
        return userService.Login(map,session);
    }

    @PostMapping("/sendMsg")
    public Response<String> sendMsg(@RequestBody User user,HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(!StringUtils.isEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //发送短信,还需要更改
            SMSUtils.sendMessage("瑞吉外卖","SMS_272605519",phone,code);
            //需要将生成的验证码保存到session中,以便验证是否正确
            session.setAttribute(phone,code);
            return Response.success();
        }
        return Response.fail();
    }

}
