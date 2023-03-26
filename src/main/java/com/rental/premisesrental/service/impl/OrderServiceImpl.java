package com.rental.premisesrental.service.impl;

import com.rental.premisesrental.mapper.OrderMapper;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 20179
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Response getOrder(Long placeId, Long beginTime, Long rentTime) {
        return null;
    }
}
