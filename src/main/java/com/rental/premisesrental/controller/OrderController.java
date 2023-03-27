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

    @PostMapping("/order/create")
    @ApiOperation(value = "下单")
    public Response createOrder(
            @ApiParam(value = "登录参数")
            @RequestBody
            OrderParam orderParam
    ) {
        return orderService.createOrder(
                orderParam.getShopId(),
                orderParam.getPlaceId(),
                orderParam.getDayOffSet(),
                orderParam.getBeginTime(),
                orderParam.getRentTime());
    }
}