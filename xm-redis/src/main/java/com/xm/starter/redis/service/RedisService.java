package com.xm.starter.redis.service;

import com.xm.starter.redis.model.RedisKeyInfo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisService extends RedisTemplate<String,Object> {
    public <T> T get(String key, Class<T> clazz) {
        Object value = this.opsForValue().get(key);
        if (clazz.isInstance(value)) {
            return clazz.cast(value);
        }
        return null;
    }

    public List<RedisKeyInfo> keyInfo(String key){
        List<RedisKeyInfo> redisKeyInfos = new ArrayList<>();
        Set<String> keys = super.keys(key+"*");
        Set<String> resultKey = new HashSet<>();
        if(keys!=null){
            for (String s : keys) {
                String k = s.replace(key,"");
                k = k.startsWith(":")?k.substring(1):k;
                resultKey.add(k.contains(":")?k.substring(0,k.indexOf(":")):k);
            }
        }
        resultKey.forEach(key1 -> redisKeyInfos.add(new RedisKeyInfo(key1)));
        return redisKeyInfos;
    }
}
