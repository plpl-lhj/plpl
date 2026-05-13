package com.sky.exception;

/**
 * 业务异常基类
 * 继承RuntimeException（非受检异常，不需要try-catch，常用于业务逻辑错误）
 * 所有自定义业务异常都继承此类，GlobalExceptionHandler统一捕获
 */
public class BaseException extends RuntimeException {
    public BaseException() {}

    public BaseException(String message) {
        super(message);
    }
}
