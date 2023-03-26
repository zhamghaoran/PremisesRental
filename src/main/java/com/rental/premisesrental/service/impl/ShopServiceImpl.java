package com.rental.premisesrental.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.rental.premisesrental.util.constant.*;

/**
 * @author 20179
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {
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
        if (id == null) {
            return Response.fail().setFailMessage("店铺id不能为空");
        }
        String key = CACHE_SHOP_KEY + id;
        //更新数据库
        shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        updateById(shop);
        //删除redis中的数据
        stringRedisTemplate.delete(key);

        return Response.success();
    }


    @Override
    public Response queryShopById(Long id) {
        Shop shop = queryWthMutex(id);
        if (shop == null) {
            return Response.fail();
        }
        return Response.success().setSuccessData(shop);
    }

    @Override
    public Response queryList(Integer page, Integer limit) {

        Page<Shop> shopPage = new Page<>(page, limit);
        List<Shop> records = shopMapper.selectPage(shopPage, null).getRecords();
        return Response.success().setSuccessData(records);

    }

    //缓存击穿
    public Shop queryWthMutex(Long id) {
        String key = CACHE_SHOP_KEY + id;
        String shopJSON = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(shopJSON)) {
            return JSONUtil.toBean(shopJSON, Shop.class);
        }
        if (shopJSON != null) {
            return null;
        }
        Shop shop = null;
        String lockKey = LOCK_SHOP_KEY + id;
        try {
            boolean isLock = tryLock(lockKey);
            if (!isLock) {
                Thread.sleep(50);
                return queryWthMutex(id);
            }
            shop = getById(key);
            if (shop == null) {
                stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(shop), CACHE_SHOP_TTL, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            unlock(lockKey);
        }

        return shop;

    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }


}

/**
 * LambdaQueryWrapper<Shop> shopLambdaQueryWrapper = new LambdaQueryWrapper<>();
 * shopLambdaQueryWrapper.eq(Shop::getId,shop.getId());
 * Shop oldShop = shopMapper.selectOne(shopLambdaQueryWrapper);
 * if (!oldShop.getOwnerId().equals(shop.getId())) {
 * return Response.fail().setFailMessage("修改失败");
 * }
 * shop.setUpdateTime(new Timestamp(System.currentTimeMillis()));
 * shopMapper.update(shop,shopLambdaQueryWrapper);
 * return Response.success();
 */