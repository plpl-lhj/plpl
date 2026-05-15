package com.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 员工登录VO
 *
 * VO（View Object）— 视图对象，用于封装返回给前端的响应数据
 * 设计原则：不暴露数据库完整字段（如password），只返回前端需要的字段
 * 使用场景：Service层查询后转换为VO，Controller通过Result<VO>返回
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "员工登录响应数据")
public class EmployeeLoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 员工ID
    @Schema(description = "员工ID")
    private Long id;

    // 登录用户名
    @Schema(description = "用户名")
    private String username;

    // 员工姓名
    @Schema(description = "姓名")
    private String name;

    // JWT访问令牌（前端需保存，后续请求携带在请求头中）
    @Schema(description = "JWT访问令牌")
    private String token;
}
