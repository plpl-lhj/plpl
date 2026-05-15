package com.sky.exception;

/**
 * 密码错误异常
 * 密码验证失败时抛出
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException(String message) {
        super(message);
    }
}
