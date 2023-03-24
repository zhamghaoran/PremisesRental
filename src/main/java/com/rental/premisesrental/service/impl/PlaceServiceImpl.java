package com.rental.premisesrental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.PlaceService;
import com.rental.premisesrental.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl  implements PlaceService{

    @Autowired
    private PlaceMapper placeMapper;

    @Override
    public Response addPlace(Place place) {
        return null
                ;

    }
}
