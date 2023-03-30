package com.rental.premisesrental.controller;

import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.service.PlaceService;
import com.rental.premisesrental.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20179
 */
@RestController
@Api(value = "场地")
@CrossOrigin
public class PlaceController {
    @Autowired
    private PlaceService placeService;


    @ApiOperation(value = "添加场地")
    @PostMapping("/add/place")
    public Response addPlace(
            @ApiParam(value = "场地信息")
            @RequestBody
            Place place
    ) {
        return placeService.addPlace(place);
    }

    @ApiOperation(value = "根据商铺id查询场地")
    @GetMapping("/get/place/list")
    public Response queryPlaceByShopId(
            @ApiParam(value = "商铺id")
            Long id
    ) {
        return placeService.queryPlaceByShopId(id);
    }


}
