package com.rental.premisesrental.util;

import lombok.Data;

/**
 * @author 20179
 */
@Data
public class constant {
    public static final String PHONE_CODE = "phone:code:";
    public static final String USER_TOKEN = "user:token:";
    public static final Long CACHE_NULL_TTL = 2L;
    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:shop:";
    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final String LOCK_PLACE_KEY = "lock:place:";
}
