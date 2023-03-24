package com.rental.premisesrental.controller;

import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.service.PlaceService;
import com.rental.premisesrental.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 20179
 */
@RestController
@Api(value = "场地Controller")
public class PlaceController {
    @Autowired
    private PlaceService placeService;

    @PostMapping("/add/place")
    public Response addPlace(
            @RequestBody
            Place place
    ) {
        return placeService.addPlace(place);
    }
}
