package com.sky.constant;

/**
 * 自动填充方法名常量
 * 避免在AutoFillAspect中硬编码字符串，集中管理便于修改
 */
public class AutoFillConstant {
    // 创建时间的setter方法名
    public static final String SET_CREATE_TIME = "setCreateTime";
    // 更新时间的setter方法名
    public static final String SET_UPDATE_TIME = "setUpdateTime";
    // 创建人的setter方法名
    public static final String SET_CREATE_USER = "setCreateUser";
    // 更新人的setter方法名
    public static final String SET_UPDATE_USER = "setUpdateUser";
}
