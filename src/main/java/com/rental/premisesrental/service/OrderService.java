package com.rental.premisesrental.service;


import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 20179
 */
@Service
public interface OrderService {

    Response getOrder(Long placeId, Long beginTime, Long rentTime);
}
