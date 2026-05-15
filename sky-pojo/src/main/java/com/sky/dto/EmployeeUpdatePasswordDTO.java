package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码DTO
 * 用于接收修改密码时的请求参数
 */
@Data
@Schema(description = "修改密码请求参数")
public class EmployeeUpdatePasswordDTO implements Serializable {

    // 原密码（用于验证身份）
    @Schema(description = "旧密码", example = "123456")
    private String oldPassword;

    // 新密码
    @Schema(description = "新密码", example = "654321")
    private String newPassword;
}
