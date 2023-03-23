package com.rental.premisesrental;

import com.rental.premisesrental.entity.User;
import com.rental.premisesrental.service.UserService;
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

}
