package com.hfutonline.mly.common.exception;

/**
 * 自定义异常
 *
 * @author chenliangliang
 * @date 2018/2/19
 */
public class MlyException extends RuntimeException {

    private static final long serialVersionUID = -4058946915425500180L;

    private String msg;
    private int code = 500;

    public MlyException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public MlyException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public MlyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public MlyException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
