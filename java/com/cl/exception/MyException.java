package com.cl.exception;

/**
 * 自定义运行时异常
 */
@SuppressWarnings("serial")
public class MyException extends RuntimeException {
    public MyException() {
        super();
    }

    public MyException(String msg) {
        super(msg);
    }

    public MyException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public MyException(Throwable throwable) {
        super(throwable);
    }
}
