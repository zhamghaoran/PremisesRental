package com.rental.premisesrental.controller;

import com.rental.premisesrental.pojo.OrderParam;
import com.rental.premisesrental.service.OrderService;
import com.rental.premisesrental.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20179
 */
@RestController
@Api(value = "订单")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    @ApiOperation(value = "下单")
    public Response createOrder(
            @ApiParam(value = "登录参数")
            @RequestBody
            OrderParam orderParam
    ) throws InterruptedException {
        return orderService.createOrder(
                Long.parseLong(orderParam.getShopId()),
                Long.parseLong(orderParam.getPlaceId()),
                orderParam.getDayOffSet(),
                orderParam.getBeginTime(),
                orderParam.getRentTime()
        );
    }

    @GetMapping("/order/get")
    @ApiOperation(value = "查询用户订单")
    public Response queryOrder() {
        return orderService.queryOrder();
    }

    @GetMapping("/order/delete/{orderId}")
    @ApiOperation(value = "删除订单")
    public Response deleteOrder(
            @ApiParam(value = "订单号")
            @PathVariable
            String orderId) throws InterruptedException {
        return orderService.deleteOrder(Long.parseLong(orderId));
    }
}
