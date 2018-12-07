package com.heiketu.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import javax.annotation.Resource;

public class CustomCacheManager implements CacheManager {

    @Resource
    private CustomCache cache;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return cache;
    }
}
