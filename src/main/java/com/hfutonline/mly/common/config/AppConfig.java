package com.hfutonline.mly.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
}
