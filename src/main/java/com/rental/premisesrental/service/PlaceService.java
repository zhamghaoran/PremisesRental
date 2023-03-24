package com.rental.premisesrental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.util.Response;

public interface PlaceService {
    Response addPlace(Place place);
}
