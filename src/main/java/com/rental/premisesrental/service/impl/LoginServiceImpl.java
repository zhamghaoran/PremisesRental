package com.rental.premisesrental.service.impl;

import com.rental.premisesrental.mapper.UserMapper;
import com.rental.premisesrental.service.LoginService;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

/**
 * @author 20179
 */
public class LoginServiceImpl implements LoginService {


    @Override
    public Response Login(String username, String password) {
        return null;

    }
}
