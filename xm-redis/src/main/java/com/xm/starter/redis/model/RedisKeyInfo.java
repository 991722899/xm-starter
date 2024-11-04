package com.xm.starter.redis.model;

import lombok.Data;

@Data
public class RedisKeyInfo {
    private String key;

    public RedisKeyInfo(String key) {
        this.key = key;
    }
}
