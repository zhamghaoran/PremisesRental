package com.rental.premisesrental.service.impl;

import com.alibaba.fastjson2.JSON;
import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.pojo.LoginParam;
import com.rental.premisesrental.service.UserService;
import com.rental.premisesrental.util.*;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.TestOnly;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.rental.premisesrental.util.constant.PHONE_CODE;
import static com.rental.premisesrental.util.constant.USER_TOKEN;

/**
 * @author 20179
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Response Login(LoginParam loginParam) {
        //获取手机号
        String phone = loginParam.getPhone();
        //获取验证码
        String code = loginParam.getCode().toString();
        log.info(code);
        //从Redis中获取保存的验证码
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
            //登录成功,就把生成得token交付给前端.
            putUserIntoRedis(user, token);
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
            //需要将生成的验证码保存到redis中进行缓存
            stringRedisTemplate.opsForValue().set(PHONE_CODE + phone, code);
            stringRedisTemplate.expire(PHONE_CODE + phone, 5, TimeUnit.MINUTES);
            log.info("验证码: ${}",code);
            return Response.success().setSuccessMessage("发送成功");
        }
        return Response.fail();
    }

    @Override
    public Response register(LoginParam loginParam) {
        String phone = loginParam.getPhone();
        Integer code = loginParam.getCode();
        String password = loginParam.getPassword();
        //存储phone和code
        if (!judgeCodeInRedis(phone,code)) {
            return Response.fail().setFailMessage("验证码错误");
        }
        //根据phone查询数据库中的用户信息
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone, phone);
        List<User> users = userMapper.selectList(userLambdaQueryWrapper);
        //不为空就直接退出
        if (!users.isEmpty()) {
            return Response.fail().setFailMessage("用户已注册");
        }
        User user = new User();
        user.setPassword(password);
        user.setPhone(phone);
        user.setUsername(loginParam.getUsername());
        userMapper.insert(user);
        String token = MD5Util.createUserToken(user);
        putUserIntoRedis(user, token);
        //将token存放到前端中
        return Response.success().setSuccessMessage("注册成功").setSuccessData(token);
    }

    @Override
    public Response LoginByPassword(LoginParam loginParam) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone, loginParam.getPhone());
        userLambdaQueryWrapper.eq(User::getPassword, loginParam.getPassword());
        userLambdaQueryWrapper.eq(User::getUsername, loginParam.getUsername());
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            return Response.fail().setFailMessage("登录失败");
        }
        String token = MD5Util.createUserToken(user);
        putUserIntoRedis(user, token);
        return Response.success().setSuccessData(token);
    }

    @Override
    public Response forgetPassword(LoginParam loginParam) {
        if (!judgeCodeInRedis(loginParam.getPhone(),loginParam.getCode())) {
            return Response.fail().setFailMessage("验证码错误");
        }
        User user = new User(
                null,
                loginParam.getUsername(),
                loginParam.getPhone(),
                loginParam.getPassword(),
                null,
                new Timestamp(System.currentTimeMillis())
        );
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,user.getPhone());
        int update = userMapper.update(user, userLambdaQueryWrapper);
        if (update > 0 )  {
            return Response.success().setSuccessMessage("修改成功");
        }
        return Response.fail().setFailMessage("修改失败");
    }


    /**
     * //这一步可以将用户的登录token放到redis中进行保存
     */
    private void putUserIntoRedis(User user, String token) {
        stringRedisTemplate.opsForValue().set(USER_TOKEN + token, JSON.toJSONString(user));
        //这一步是用来给token设置时间
        stringRedisTemplate.expire(USER_TOKEN + token, 1, TimeUnit.HOURS);
    }

    private boolean judgeCodeInRedis(String phone, Integer code) {
        String s = stringRedisTemplate.opsForValue().get(PHONE_CODE + phone);
        if (StringUtils.isEmpty(s) || code == null || !s.equals(code.toString())) {
            return false;
        }
        return true;
    }
}
