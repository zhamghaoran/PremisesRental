package com.rental.premisesrental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.entity.Place;
import com.rental.premisesrental.mapper.PlaceMapper;
import com.rental.premisesrental.service.PlaceService;
import org.springframework.stereotype.Service;

@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService{
}
