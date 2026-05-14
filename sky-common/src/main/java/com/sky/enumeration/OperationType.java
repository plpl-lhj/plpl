package com.sky.enumeration;

/**
 * 操作类型枚举
 * 用于 @AutoFill 注解，区分新增和更新操作
 * INSERT — 新增操作，需填充创建时间和创建人
 * UPDATE — 更新操作，只需填充更新时间和更新人
 */
public enum OperationType {
    INSERT,
    UPDATE
}
