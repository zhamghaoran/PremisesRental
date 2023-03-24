package com.rental.premisesrental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rental.premisesrental.entity.Shop;
import com.rental.premisesrental.mapper.ShopMapper;
import com.rental.premisesrental.service.ShopService;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author 20179
 */
@Service
public class ShopServiceImpl  implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public Response addShop(Shop shop) {
        shop.setOwnerId(UserHolder.getCurrentUser().getId());
        int insert = shopMapper.insert(shop);
        if (insert < 1) {
            return Response.fail();
        }
        return Response.success().setSuccessMessage("添加成功");
    }

    @Override
    public Response updateShop(Shop shop) {
        LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shopLambdaQueryWrapper.eq(Shop::getId,shop.getId());
        Shop oldShop = shopMapper.selectOne(shopLambdaQueryWrapper);
        if (!oldShop.getOwnerId().equals(shop.getId())) {
            return Response.fail().setFailMessage("修改失败");
        }
        shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        shopMapper.update(shop,shopLambdaQueryWrapper);
        return Response.success();
    }
}
