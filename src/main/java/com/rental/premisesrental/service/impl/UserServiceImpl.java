package com.rental.premisesrental.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.pojo.LoginParam;
import com.rental.premisesrental.service.UserService;
import com.rental.premisesrental.util.MD5Util;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.SMSUtils;
import com.rental.premisesrental.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.rental.premisesrental.util.constant.PHONE_CODE;
import static com.rental.premisesrental.util.constant.USER_TOKEN;

/**
 * @author 20179
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Response Login(LoginParam loginParam) {
        //获取手机号
        String phone = loginParam.getPhone();
        //获取验证码
        String code = loginParam.getCode().toString();
        log.info(code);
        //从Redis中获取保存的验证码
//        Object codeInRedis = session.getAttribute(phone);
        String codeInRedis = stringRedisTemplate.opsForValue().get(PHONE_CODE + phone);
        //进行验证码的比对（页面提交的验证码和Redis中保存的验证码比对）
        if (codeInRedis != null && codeInRedis.equals(code)) {
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userMapper.selectOne(queryWrapper);
            if (user == null) {
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                userMapper.insert(user);
            }
            String token = MD5Util.createUserToken(user);
            putUserIntoRedis(user,token);
            return Response.success().setSuccessData(token);
        }
        return Response.fail();
    }

    @Override
    public Response sendMsg(String phone) {
        if (!ObjectUtils.isEmpty(phone)) {
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            //发送短信,还需要更改
            SMSUtils.sendMessage("PremisesRent", "SMS_272605519", phone, code);
            //需要将生成的验证码保存到session中,以便验证是否正确
            stringRedisTemplate.opsForValue().set(PHONE_CODE + phone,code);
            stringRedisTemplate.expire(PHONE_CODE + phone,5, TimeUnit.MINUTES);
            return Response.success().setSuccessMessage("发送成功");
        }
        return Response.fail();
    }

    @Override
    public Response register(LoginParam loginParam) {
        String phone = loginParam.getPhone();
        Integer code = loginParam.getCode();
        String s = stringRedisTemplate.opsForValue().get(PHONE_CODE + phone);
        if (StringUtils.isEmpty(s) || code == null || !s.equals(code.toString())) {
            return Response.fail();
        }
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,phone);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        if (!users.isEmpty()) {
            return Response.fail().setFailMessage("用户已注册");
        }
        User user = new User();
        user.setPhone(phone);
        user.setUsername(loginParam.getUsername());
        userMapper.insert(user);
        String token = MD5Util.createUserToken(user);
        putUserIntoRedis(user,token);
        return Response.success().setSuccessData(token);
    }

    private void putUserIntoRedis(User user,String token) {
        stringRedisTemplate.opsForValue().set(USER_TOKEN + token, String.valueOf(user));
    }
}
