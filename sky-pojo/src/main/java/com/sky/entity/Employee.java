package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 * 对应数据库employee表，封装员工的完整信息
 *
 * @Data         — Lombok核心注解，编译时自动生成getter/setter/toString/equals/hashCode方法
 * @AllArgsConstructor — Lombok注解，生成包含所有字段的构造函数
 * @NoArgsConstructor  — Lombok注解，生成无参构造函数（MyBatis反序列化等场景必须）
 * @Builder      — Lombok注解，生成建造者模式代码，支持链式构造对象（如Employee.builder().id(1L).build()）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee implements Serializable {

    /**
     * @Serial — Java 14引入，标记该字段为序列化版本号
     * 作用：反序列化时校验版本一致性，避免版本不兼容导致的反序列化失败
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // 主键ID（数据库自增）
    private Long id;

    // 员工姓名
    private String name;

    // 登录用户名（唯一约束）
    private String username;

    // 登录密码（Argon2id加密存储，不可逆）
    private String password;

    // 联系电话
    private String phone;

    // 性别（男/女）
    private String sex;

    // 身份证号码（唯一约束）
    private String idNumber;

    // 账号状态（1:启用 0:禁用）
    private Integer status;

    // 记录创建时间（自动填充）
    private LocalDateTime createTime;

    // 记录最后更新时间（自动填充）
    private LocalDateTime updateTime;

    // 创建人ID（关联employee.id）
    private Long createUser;

    // 最后更新人ID（关联employee.id）
    private Long updateUser;
}
