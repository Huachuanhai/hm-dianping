package com.hmdp.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 21027
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
