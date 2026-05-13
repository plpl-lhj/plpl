package com.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工登录返回的数据格式
 * VO：View Object，视图对象，用于返回给客户端的数据封装
 * @Schema - Swagger注解，description属性会显示在Swagger UI中
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    // 主键值
    @Schema(description = "主键值")
    private Long id;

    // 用户名
    @Schema(description = "用户名")
    private String username;

    // 姓名
    @Schema(description = "姓名")
    private String name;

    // jwt令牌
    @Schema(description = "jwt令牌")
    private String token;
}