package com.hfutonline.mly.common.utils;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenliangliang
 * @date 2018/2/18
 */
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -8044673729630271207L;

    public Result(int code, String msg) {
        this.put("code", code);
        this.put("msg", msg);
    }

    public Result(HttpStatus status){
        this(status.value(),status.getReasonPhrase());
    }

   public static Result OK(String msg){
        return new Result(200,msg);
   }

   public static Result OK(){
      return   OK("success");
   }

   public static Result OK(Map<String,Object> data){
        Result res=OK();
        res.putAll(data);
        return res;
   }

   public static Result error(HttpStatus status){
      return new Result(status);
   }

   public static Result error(HttpStatus status,String msg){
        return new Result(status.value(),msg);
   }

    public static Result error(String msg){
        return new Result(500,msg);
    }

    public static Result error(){
        return error("未知异常，请联系管理员");
    }



    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
