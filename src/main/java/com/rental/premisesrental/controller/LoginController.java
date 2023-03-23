package com.rental.premisesrental.controller;

import com.rental.premisesrental.service.LoginService;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20179
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Response login(@RequestBody String username,@RequestBody String password) {
        return loginService.login(username,password);
    }
    public Response register(@RequestBody String username,@RequestBody String password) {
        return loginService.register(username,password);
    }

}
