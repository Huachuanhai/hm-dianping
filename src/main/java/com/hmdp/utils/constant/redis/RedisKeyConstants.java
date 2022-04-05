package com.hmdp.utils.constant.redis;

/**
 * redis键的常量类
 *
 * @author 21027
 * @date 2022/3/29 20:14
 */
public class RedisKeyConstants {

    /**
     * 登录发送手机验证码的前缀
     */
    public static final String LOGIN_SEND_MESSAGE_Key = "login_send_message_key-";

    /**
     * 用户登录的token,redis前缀
     */
    public static final String LOGIN_TOKEN = "login_token-";

    public static final String CACHE_SHOP_KEY = "cache:shop:";

    public static final String CACHE_SHOP_TYPE_KEY = "cache:shop:type";

    public static final String LOCK_SHOP_KEY = "lock:shop:";



}
