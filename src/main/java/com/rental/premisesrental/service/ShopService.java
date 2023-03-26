package com.rental.premisesrental.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rental.premisesrental.entity.Shop;
import com.rental.premisesrental.util.Response;

public interface ShopService  {
    Response addShop(Shop shop);

    Response updateShop(Shop shop);


    Response queryShopById(Long id);

    Response queryList(Integer page, Integer limit);
}
