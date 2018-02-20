package com.hfutonline.mly.common.exception;

/**
 * @author chenliangliang
 * @date 2018/2/20
 */
public class ParamsException extends RuntimeException {


    private static final long serialVersionUID = -4058946915425500180L;

    private String msg;

    public ParamsException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ParamsException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
