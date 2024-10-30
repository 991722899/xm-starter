package com.xm.starter.redis.service;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisService extends RedisTemplate {
    public <T> T get(String key, Class<T> clazz) {
        Object value = this.opsForValue().get(key);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }
}
