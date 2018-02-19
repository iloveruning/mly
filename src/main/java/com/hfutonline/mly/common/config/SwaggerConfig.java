/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.hfutonline.mly.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2018-01-16
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//            .apiInfo(apiInfo())
//            .select()
//            //加了ApiOperation注解的类，生成接口文档
//            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//            //包下的类，生成接口文档
//            .apis(RequestHandlerSelectors.basePackage("com.hfutonline.mly.modules.sys.controller"))
//            //.paths(PathSelectors.any())
//            .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//            .title("明理苑后台管理系统")
//            .description("明理苑后台管理系统文档")
//            .termsOfServiceUrl("http://www.renren.io")
//            .version("3.1.0")
//            .build();
//    }

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SPRING_WEB)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hfutonline.mly.modules.sys.controller"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("明理苑后台管理系统")
                .contact(new Contact("明理苑技术部","","chenliangliang68@163.com"))
                .version("1.0")
                .build();
    }

}