package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 * @Data - Lombok注解，自动生成getter/setter/toString/equals/hashCode
 * @AllArgsConstructor - 生成全参构造函数
 * @NoArgsConstructor - 生成无参构造函数
 * @Builder - 生成建造者模式代码，链式构造对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    // 主键
    private Long id;
    // 姓名
    private String name;
    // 用户名(登录用)
    private String username;
    // 密码(加密存储)
    private String password;
    // 手机号
    private String phone;
    // 性别
    private String sex;
    // 身份证号
    private String idNumber;
    // 账号状态(1:启用, 0:禁用)
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 创建人id
    private Long createUser;
    // 更新人id
    private Long updateUser;
}
