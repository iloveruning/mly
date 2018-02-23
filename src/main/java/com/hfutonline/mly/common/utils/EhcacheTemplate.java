package com.hfutonline.mly.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

/**
 * ehcache缓存模板
 *
 * @author chenliangliang
 * @date 2018/2/23
 */
@Slf4j
@Component
public class EhcacheTemplate {

    private EhCacheCacheManager cacheCacheManager;

    @Autowired
    protected EhcacheTemplate(EhCacheCacheManager cacheCacheManager) {
        this.cacheCacheManager = cacheCacheManager;
    }


    /**
     * 读写缓存:如果命中缓存，则返回缓存中的值，否则执行call方法返回值并写入缓存
     */
    public <T> T cacheable(String cacheName, String key, Callable<T> valueLoader) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            try {
                log.warn("named " + cacheName + " cache is null");
                return valueLoader.call();
            } catch (Exception e) {
                throw new RuntimeException("failed to load value from" + valueLoader);
            }
        }

        return cache.get(key, valueLoader);
    }


    /**
     * 写缓存
     */
    public void cachePut(String cacheName, String key, Object value) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            return;
        }

        cache.put(key, value);
    }

    /**
     * 清除缓存
     */
    public void cacheEvict(String cacheName, String key) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            return;
        }
        cache.evict(key);
    }

    public <T> T cacheGet(String cacheName, String key, Class<T> type) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            throw new RuntimeException("no cache named : " + cacheName + " found!");
        }

        return cache.get(key, type);
    }


    public void clear(String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }


    private Cache getCache(String cacheName) {

        return this.cacheCacheManager.getCache(cacheName);
    }

}
