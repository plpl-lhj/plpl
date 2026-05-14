package com.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 员工登录返回的数据格式
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {
    // serialVersionUID — 序列化版本号，用于反序列化时校验版本一致性
    @Serial
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
