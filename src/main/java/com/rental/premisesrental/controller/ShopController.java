package com.rental.premisesrental.controller;

import com.rental.premisesrental.entity.Shop;
import com.rental.premisesrental.service.ShopService;
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
@Api(value = "商铺")
@CrossOrigin
public class ShopController {
    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "添加商铺")
    @PostMapping("/shop/add")
    public Response addShop(
            @ApiParam(value = "商铺信息")
            @RequestBody
            Shop shop
    ) {
        return shopService.addShop(shop);
    }

    @PostMapping("/shop/update")
    @ApiOperation(value = "更新商铺")
    public Response updateShop(
            @RequestBody
            @ApiParam(value = "商铺信息")
            Shop shop
    ) {
        return shopService.updateShop(shop);
    }


    @GetMapping("/shop/{id}")
    @ApiOperation(value = "查询商铺")
    public Response queryShopById(
            @ApiParam(value = "商铺信息")
            @PathVariable("id") Long id
    ) {
        return shopService.queryShopById(id);
    }

    @GetMapping("/shop/list/{page}/{limit}")
    @ApiOperation(value = "获取商铺列表")
    public Response queryShopList(
            @ApiParam(value = "页码")
            @PathVariable("page")Integer page,
            @ApiParam(value = "每页上限")
            @PathVariable("limit") Integer limit) {
        return shopService.queryList(page,limit);
    }
}
