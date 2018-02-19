package com.hfutonline.mly.common.config;

import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author chenliangliang
 * @date 2018/1/8
 */
@Configuration
@EnableCaching
public class CacheConfig {

    private Logger log= LoggerFactory.getLogger(CacheConfig.class);
    @Bean
    public EhCacheCacheManager cacheManager(CacheManager cacheManager) {
        log.info("ehCacheCacheManager注入成功！");
        //log.info( Arrays.toString(cacheManager.getCacheNames()));
        return new EhCacheCacheManager(cacheManager);
    }

    @Bean
    public EhCacheManagerFactoryBean ehcache() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }
}
