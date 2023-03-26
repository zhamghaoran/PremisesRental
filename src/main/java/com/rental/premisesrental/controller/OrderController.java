package com.rental.premisesrental.controller;

import com.rental.premisesrental.pojo.OrderParam;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20179
 */
@RestController
@Api(value = "订单")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "下单")
    @PostMapping("/order/get")
    public Response getOrder(
            @RequestBody OrderParam orderParam

    ) {
        return orderService.getOrder(orderParam.getPlaceId(),orderParam.getBeginTime(),orderParam.getRentTime());
    }
}
