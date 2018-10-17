package com.hfutonline.mly.common.exception;

import com.hfutonline.mly.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author chenliangliang
 * @date 2018/2/19
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    /**
     * 处理自定义异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MlyException.class)
    public Result handleMlyException(MlyException e) {
        log.warn("拦截到业务异常:{}", e.getMsg());
        return new Result(e.getCode(), e.getMsg());
    }


    /**
     * 处理自定义异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(TransactionException.class)
    public Result handleTransactionException(TransactionException e) {
        log.warn("拦截到数据异常:{}", e.getMessage());
        return Result.error("数据异常: "+e.getMessage());
    }

    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({BindException.class})
    public Result handleBindException(BindException e) {
        return getResult(e.getBindingResult());
    }

    private Result getResult(BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        StringBuilder error = new StringBuilder();
        allErrors.forEach(it -> error.append(it.getDefaultMessage()).append(","));
        error.deleteCharAt(error.length() - 1);
        log.warn("拦截到参数异常:{}", error.toString());
        return Result.error(HttpStatus.BAD_REQUEST, "参数异常：" + error.toString());
    }

    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class})
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return getResult(e.getBindingResult());
    }


    /**
     * 拦截参数异常
     *
     * @param e 参数异常
     * @return Result
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("拦截到用户消息输入异常:{}", e.getMessage(), e);
        return Result.error(HttpStatus.BAD_REQUEST, "消息格式错误!");
    }
}
