package com.sky.exception;

/**
 * 账号被锁定异常
 * 当用户账号状态为禁用（status=0）时抛出
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException(String message) {
        super(message);
    }
}
