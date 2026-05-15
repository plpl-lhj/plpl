package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工登录DTO
 *
 * DTO（Data Transfer Object）— 数据传输对象，用于接收前端请求参数
 * 设计原则：只包含业务所需的字段，不暴露数据库完整结构
 * 使用场景：Controller方法参数，通过@RequestBody自动反序列化JSON
 *
 * @Schema  — SpringDoc OpenAPI注解，用于生成Swagger接口文档
 *           description属性：在Swagger UI中显示字段说明
 *           类级别@Schema：描述整个DTO的用途
 *           字段级别@Schema：描述每个字段的含义
 */
@Data
@Schema(description = "员工登录请求参数")
public class EmployeeLoginDTO implements Serializable {

    // 登录用户名
    @Schema(description = "用户名", example = "admin")
    private String username;

    // 登录密码（明文传输，后端验证后不存储）
    @Schema(description = "密码", example = "123456")
    private String password;
}
