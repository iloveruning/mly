package com.hfutonline.mly.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenliangliang
 * @date 2018/2/17
 */
@Configuration
public class ThymeleafConfig {

    /**
     *在HTML页面中写shiro标签
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
