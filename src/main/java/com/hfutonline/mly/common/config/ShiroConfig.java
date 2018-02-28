package com.hfutonline.mly.common.config;


import com.hfutonline.mly.modules.sys.shiro.LoginRetryCredentialsMatcher;
import com.hfutonline.mly.modules.sys.shiro.ShiroRealm;
import com.hfutonline.mly.modules.sys.shiro.filter.SessionCountControllerFilter;
import com.hfutonline.mly.modules.sys.shiro.tool.ShiroKit;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro权限管理的配置
 *
 * @author chenliangliang
 * @date 2018/1/8
 */
@Configuration
public class ShiroConfig {

    private Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 缓存管理器 使用Ehcache实现
     */
    @Bean(name = "shiroCacheManager")
    public CacheManager cacheManager(/*EhCacheCacheManager cacheManager*/) {

        log.info("shiroCacheManager注入成功！");
        EhCacheManager ehCacheManager = new EhCacheManager();
        // ehCacheManager.setCacheManager(cacheManager.getCacheManager());
        ehCacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        ehCacheManager.init();
        return ehCacheManager;

    }


    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setCacheManager(cacheManager());
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        return sessionDAO;
    }


    /**
     * 凭证匹配器
     */
    @Bean
    public LoginRetryCredentialsMatcher credentialsMatcher() {
        LoginRetryCredentialsMatcher credentialsMatcher = new LoginRetryCredentialsMatcher();
        credentialsMatcher.setCacheManager(cacheManager());
        credentialsMatcher.setLoginRetryCacheName("loginRetry");
        credentialsMatcher.setMaxRetryCount(5);
        credentialsMatcher.setHashAlgorithmName(ShiroKit.HASH_ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(ShiroKit.HASH_ITERATIONS);
        return credentialsMatcher;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager rememberMeManager,
                                                     CacheManager cacheManager, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        securityManager.setCacheManager(cacheManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * session管理器
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setCacheManager(cacheManager());
        sessionManager.setSessionDAO(sessionDAO());
        //设置session失效时间
        sessionManager.setGlobalSessionTimeout(3600000);
        //多久检测一次失效的session
        sessionManager.setSessionValidationInterval(1800000);
        //删除无效的session
        sessionManager.setDeleteInvalidSessions(true);
        //允许session自动验证
        sessionManager.setSessionValidationSchedulerEnabled(true);

        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);
        cookie.setName("shiroCookie");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        cookie.setMaxAge(-1);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(cookie);

        return sessionManager;
    }


    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie cookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("Z3VucwAAAAAAAAAAAAAAAA=="));
        manager.setCookie(cookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(604800);//7天
        return simpleCookie;
    }

    /**
     * 首先，我们不能将自定义Filter让Spring管理了，先去掉注解@Bean
     * <p>
     * 第二就是：使用ShiroFilterFactoryBean进行注入，就是上下级的关系了，
     */
    //@Bean
    private SessionCountControllerFilter sessionCountControllerFilter() {
        SessionCountControllerFilter sessionCountControllerFilter = new SessionCountControllerFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        sessionCountControllerFilter.setCacheManager(cacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        sessionCountControllerFilter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        sessionCountControllerFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        sessionCountControllerFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        sessionCountControllerFilter.setKickoutUrl("/kickout");
        log.info("sessionCountControllerFilter注入成功");
        return sessionCountControllerFilter;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        /**
         * 默认的登陆访问url
         */
        shiroFilter.setLoginUrl("/login");
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.setSuccessUrl("/index");
        /**
         * 没有权限跳转的url
         */
        shiroFilter.setUnauthorizedUrl("/error");
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         *
         * 顺序从上到下,优先级依次降低
         *
         */

        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        filtersMap.put("kickout", sessionCountControllerFilter());
        shiroFilter.setFilters(filtersMap);

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/statics/**", "anon");
        hashMap.put("/favicon.ico", "anon");
        hashMap.put("/webjars/**", "anon");

        hashMap.put("/admin/**","anon");
        hashMap.put("/api/**","anon");
        hashMap.put("/health/**","anon");
        hashMap.put("/env/**","anon");
        hashMap.put("/metrics/**","anon");
        hashMap.put("/trace/**","anon");
        hashMap.put("/dump/**","anon");
        hashMap.put("/jolokia/**","anon");
        hashMap.put("/info/**","anon");
        hashMap.put("/logfile/**","anon");
        hashMap.put("/refresh/**","anon");
        hashMap.put("/flyway/**","anon");
        hashMap.put("/liquibase/**","anon");
        hashMap.put("/heapdump/**","anon");
        hashMap.put("/loggers/**","anon");
        hashMap.put("/auditevents/**","anon");

        hashMap.put("/open/api/**","anon");

        hashMap.put("/login", "anon");
        hashMap.put("/captcha", "anon");
        hashMap.put("/sys/login", "anon");
        hashMap.put("/register", "anon");
        hashMap.put("/kickout", "anon");
        hashMap.put("/logout","logout");


        hashMap.put("/druid/sql.html","perms[sys:sql]");
        //hashMap.put("/","user");filterMap.put("/swagger/**", "anon");
        //        filterMap.put("/v2/api-docs", "anon");
        //        filterMap.put("/swagger-ui.html", "anon");
        //        filterMap.put("/webjars/**", "anon");
        //        filterMap.put("/swagger-resources/**", "anon");
        //
        //        filterMap.put("/statics/**", "anon");
        //        filterMap.put("/login.html", "anon");
        //        filterMap.put("/sys/login", "anon");
        //        filterMap.put("/favicon.ico", "anon");
        //        filterMap.put("/captcha.jpg", "anon");
        //        filterMap.put("/**", "authc");
        //hashMap.put("/index", "user");
        //hashMap.put("/global/sessionError", "anon");
        //验证码
        // hashMap.put("/kaptcha", "anon");
        //hashMap.put("/**", "user,kickout");
        hashMap.put("/**", "authc");
        //hashMap.put("/**", "kickout");
        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;

    }

    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(securityManager);
        return bean;
    }

    /**
     * Shiro生命周期处理器:
     * 用于在实现了Initializable接口的Shiro bean初始化时调用Initializable接口回调(例如:UserRealm)
     * 在实现了Destroyable接口的Shiro bean销毁时调用 Destroyable接口回调(例如:DefaultSecurityManager)
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
