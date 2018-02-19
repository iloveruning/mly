package com.hfutonline.mly.modules.sys.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证器，增加了登录次数校验功能
 *
 * @author chenliangliang
 * @date 2018/1/10
 */
public class LoginRetryCredentialsMatcher extends HashedCredentialsMatcher {


    private Logger logger = LoggerFactory.getLogger(LoginRetryCredentialsMatcher.class);

    private Cache<String, AtomicInteger> cache;

    private String loginRetryCacheName = "loginRetry";

    private int maxRetryCount = 5;


    public void setLoginRetryCacheName(String loginRetryCacheName) {
        this.loginRetryCacheName = loginRetryCacheName;
    }

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public void setCacheManager(CacheManager cacheManager) {
        if (cacheManager != null) {
            this.cache = cacheManager.getCache(loginRetryCacheName);
        }
    }

    public LoginRetryCredentialsMatcher() {
        super();
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        String name = (String) token.getPrincipal();
        AtomicInteger retryCount = cache.get(name);

        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            cache.put(name,retryCount);
        }

        if (retryCount.incrementAndGet() > maxRetryCount) {
            logger.warn("用户: [{}] 登录重试次数超过{}次", name, maxRetryCount);
            throw new ExcessiveAttemptsException("username: " + name + " tried to login more than 5 times in period");
        }
        boolean isMatch = super.doCredentialsMatch(token, info);

        if (isMatch) {
            cache.remove(name);
        }
        return isMatch;
    }
}
