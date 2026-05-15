package com.sky.constant;

/**
 * 自动填充方法名常量类
 * 定义实体类setter方法名，用于AutoFillAspect切面的反射调用
 * 集中管理避免硬编码，便于维护
 */
public class AutoFillConstant {

    /** setCreateTime方法名 */
    public static final String SET_CREATE_TIME = "setCreateTime";

    /** setUpdateTime方法名 */
    public static final String SET_UPDATE_TIME = "setUpdateTime";

    /** setCreateUser方法名 */
    public static final String SET_CREATE_USER = "setCreateUser";

    /** setUpdateUser方法名 */
    public static final String SET_UPDATE_USER = "setUpdateUser";
}
