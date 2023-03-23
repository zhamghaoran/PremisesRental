package com.rental.premisesrental.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.entity.Shop;
import com.rental.premisesrental.mapper.ShopMapper;
import com.rental.premisesrental.service.ShopService;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {
}
