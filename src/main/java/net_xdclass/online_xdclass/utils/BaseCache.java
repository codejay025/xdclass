package net_xdclass.online_xdclass.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 定义缓存
 */
@Component
public class BaseCache {

    // 初始化缓存属性，10分钟的
    private Cache<String, Object> tenMinCache = CacheBuilder.newBuilder()
            // 设置初始化容量，最大容量
            .initialCapacity(10)
            .maximumSize(100)
            // 设置过期时间，10min
            .expireAfterWrite(600, TimeUnit.SECONDS)
            // 线程并发度
            .concurrencyLevel(5)
            .recordStats()
            .build();

    private Cache<String, Object> oneHourCache = CacheBuilder.newBuilder()
            // 设置初始化容量，最大容量
            .initialCapacity(10)
            .maximumSize(100)
            // 设置过期时间，10min
            .expireAfterWrite(3600, TimeUnit.SECONDS)
            // 线程并发度
            .concurrencyLevel(5)
            .recordStats()
            .build();

    public Cache<String, Object> getTenMinCache() {
        return tenMinCache;
    }

    public void setTenMinCache(Cache<String, Object> tenMinCache) {
        this.tenMinCache = tenMinCache;
    }

    public Cache<String, Object> getOneHourCache() {
        return oneHourCache;
    }

    public void setOneHourCache(Cache<String, Object> oneHourCache) {
        this.oneHourCache = oneHourCache;
    }
}
