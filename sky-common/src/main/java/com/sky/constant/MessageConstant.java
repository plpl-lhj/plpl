package com.sky.constant;

/**
 * 消息常量类
 * 定义业务异常提示信息，统一管理便于国际化和维护
 */
public class MessageConstant {

    /** 账号不存在 */
    public static final String ACCOUNT_NOT_FOUND = "账号不存在";

    /** 密码错误 */
    public static final String PASSWORD_ERROR = "密码错误";

    /** 账号被锁定 */
    public static final String ACCOUNT_LOCKED = "账号被锁定";

    /** 数据已存在（唯一键冲突） */
    public static final String ALREADY_EXIST = "已存在";

    /** 服务器内部错误 */
    public static final String UNKNOWN_ERROR = "服务器错误";
}