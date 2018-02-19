package com.hfutonline.mly.common.exception;

import com.hfutonline.mly.common.utils.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chenliangliang
 * @date 2018/2/19
 */
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * 处理自定义异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MlyException.class)
    public Result handleRRException(MlyException e){
        return new Result(e.getCode(),e.getMsg());
    }
}
