package com.rental.premisesrental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rental.premisesrental.entity.Shop;
import com.rental.premisesrental.mapper.ShopMapper;
import com.rental.premisesrental.service.ShopService;
import com.rental.premisesrental.util.Response;
import com.rental.premisesrental.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * @author 20179
 */
@Service
public class ShopServiceImpl  extends ServiceImpl<ShopMapper,Shop> implements ShopService {
    @Autowired
    private ShopMapper shopMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;

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
        Long id = shop.getId();
        if (id==null){
            return Response.fail();
        }
        String key="shop:"+ id;
        stringRedisTemplate.delete(key);
        shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        updateById(shop);
        return Response.success();
    }



}

/**
 *
 * LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = new LambdaQueryWrapper<>();
 *         shopLambdaQueryWrapper.eq(Shop::getId,shop.getId());
 *         Shop oldShop = shopMapper.selectOne(shopLambdaQueryWrapper);
 *         if (!oldShop.getOwnerId().equals(shop.getId())) {
 *             return Response.fail().setFailMessage("修改失败");
 *         }
 *         shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));
 *         shopMapper.update(shop,shopLambdaQueryWrapper);
 *         return Response.success();
 *
 */