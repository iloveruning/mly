package com.hfutonline.mly.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author chenliangliang
 * @date 2018/2/18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
