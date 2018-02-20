package com.hfutonline.mly.common.exception;

/**
 * @author chenliangliang
 * @date 2018/2/20
 */
public class TransactionException extends RuntimeException {


    private static final long serialVersionUID = 7461087836838067996L;


    public TransactionException(String msg) {
        super(msg);
    }

    public TransactionException(String msg, Throwable e) {
        super(msg, e);
    }

}
