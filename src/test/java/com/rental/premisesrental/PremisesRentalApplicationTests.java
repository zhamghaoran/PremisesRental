package com.rental.premisesrental;

import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.service.UserService;
import com.rental.premisesrental.utils.SMSUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class PremisesRentalApplicationTests {
    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        List<User> list = userService.list();
        System.out.println(list);

    }

    @Test
    void testSendMsg(){
        //注意,这里的签名必须和申请的一致
        SMSUtils.sendMessage("PremisesRent","SMS_272605519","18233050395","6666");
    }
}
