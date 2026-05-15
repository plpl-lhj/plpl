package com.sky.exception;

/**
 * 业务异常基类
 * 继承RuntimeException（非受检异常，无需try-catch）
 * 所有自定义业务异常均继承此类，由GlobalExceptionHandler统一捕获处理
 */
public class BaseException extends RuntimeException {

    /**
     * 无参构造函数
     */
    public BaseException() {
    }

    /**
     * 带错误信息的构造函数
     *
     * @param message 错误信息
     */
    public BaseException(String message) {
        super(message);
    }
}
