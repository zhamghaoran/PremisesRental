package com.rental.premisesrental.service;

import com.rental.premisesrental.util.Response;
import org.springframework.stereotype.Service;

/**
 * @author 20179
 */
@Service
public interface LoginService {
    Response login(String username, String password);

    Response register(String username, String password);
}
