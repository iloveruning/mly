package com.hfutonline.mly.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Properties;

/**
 * @author chenliangliang
 * @date 2018/2/23
 */
@Configuration
public class AppConfig {

    @Bean(name = "myExecutor")
    public ThreadPoolTaskExecutor MyExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(25);
        executor.initialize();

        return executor;
    }


    /**
     * 配置过滤器
     */
   /* @Bean
    public FilterRegistrationBean filterRegistrationBean(ApiRealm apiFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ApiFilter(apiFilter));
        registration.addUrlPatterns("/open/api/*");
        registration.addInitParameter("cacheName", "api");
        registration.setName("apiFilter");
        return registration;
    }*/

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
