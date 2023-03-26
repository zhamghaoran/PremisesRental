package com.rental.premisesrental.util;

import lombok.Data;

/**
 * @author 20179
 */
@Data
public class constant {
    public static final String SPORTS_TYPE_BADMINTON = "badminton";
    public static final String SPORTS_TYPE_TABLE_TENNIS = "table tennis";
    public static final String SPORTS_TYPE_BASKETBALL = "basketball";
    public static final String SPORTS_TYPE_FOOTBALL = "football";
    public static final String PHONE_CODE = "phone:code:";
    public static final String USER_TOKEN = "user:token:";
    public static final Long CACHE_NULL_TTL = 2L;
    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:shop:";
    public static final String LOCK_SHOP_KEY = "lock:shop:";
}
