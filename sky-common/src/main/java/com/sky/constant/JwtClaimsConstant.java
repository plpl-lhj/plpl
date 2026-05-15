package com.sky.constant;

/**
 * JWT Claims常量类
 * 定义JWT Payload中存储的用户信息Key
 * Claims是JWT的核心组成部分，用于携带用户身份信息
 */
public class JwtClaimsConstant {

    /** 员工ID */
    public static final String EMPLOYEE_ID = "employeeId";

    /** 用户ID（C端用户） */
    public static final String USER_ID = "userId";

    /** 手机号 */
    public static final String PHONE = "phone";

    /** 用户名 */
    public static final String USERNAME = "username";

    /** 姓名 */
    public static final String NAME = "name";
}
