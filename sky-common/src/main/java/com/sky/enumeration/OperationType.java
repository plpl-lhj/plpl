package com.sky.enumeration;

/**
 * 操作类型枚举
 * 用于@AutoFill注解，标识Mapper方法的操作类型
 * 切面根据此枚举值决定填充哪些审计字段
 */
public enum OperationType {

    /**
     * 新增操作
     * 需填充：createTime、createUser、updateTime、updateUser
     */
    INSERT,

    /**
     * 更新操作
     * 需填充：updateTime、updateUser
     */
    UPDATE
}
