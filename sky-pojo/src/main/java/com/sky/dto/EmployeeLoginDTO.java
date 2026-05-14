package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 员工登录时传递的数据模型
 * @Schema - Swagger注解，description属性会显示在Swagger UI中
 */
@Data
@Schema(description = "员工登录时传递的数据模型")
public class EmployeeLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // 用户名
    @Schema(description = "用户名")
    private String username;

    // 密码
    @Schema(description = "密码")
    private String password;
}
