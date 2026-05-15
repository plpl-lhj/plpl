package com.sky.exception;

/**
 * 账号不存在异常
 * 根据用户名查询不到记录时抛出
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
