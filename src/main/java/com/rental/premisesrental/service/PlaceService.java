package com.rental.premisesrental.service;

import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.util.Response;

public interface PlaceService {
    Response addPlace(Place place);

    Response queryPlaceByShopId(Long id);

}
