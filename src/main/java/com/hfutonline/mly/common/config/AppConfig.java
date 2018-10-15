package com.hfutonline.mly.common.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;

/**
 * @author chenliangliang
 * @date 2018/2/23
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "myExecutor")
    public ThreadPoolTaskExecutor MyExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(25);
        executor.initialize();

        return executor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/open/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST")
                .allowCredentials(false)
                .maxAge(7200);
    }

    //@Bean
    public HttpMessageConverter messageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(Charset.forName("UTF-8"));
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.IgnoreNonFieldGetter,
                SerializerFeature.IgnoreErrorGetter,
                SerializerFeature.SkipTransientField);
        config.setFeatures(Feature.SupportNonPublicField);

        converter.setFastJsonConfig(config);

        return converter;
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



}
