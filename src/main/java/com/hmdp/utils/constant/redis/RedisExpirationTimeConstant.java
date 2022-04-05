package com.hmdp.utils.constant.redis;

/**
 * redis过期时间的常量类
 *
 * @author 21027
 * @date 2022/3/29 22:17
 */
public class RedisExpirationTimeConstant {

    /**
     * 登录发送短信验证码的key的过期时间:2分钟
     */
    public static final Long LOGIN_SMS_TTL = 2L;

    /**
     * 用户登录有效期：30分钟
     */
    public static final Long LOGIN_USER_TTL = 30L;

    public static final Long CACHE_SHOP_TTL = 30L;

    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long LOCK_SHOP_TTL = 10L;



}
