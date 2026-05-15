package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改员工DTO
 * 用于接收修改员工信息时的请求参数（ID必填）
 */
@Data
@Schema(description = "修改员工请求参数")
public class EmployeeUpdateDTO implements Serializable {

    // 员工ID（必填，用于定位要修改的记录）
    @Schema(description = "员工ID", example = "1")
    private Long id;

    // 身份证号码
    @Schema(description = "身份证号", example = "110101199001011234")
    private String idNumber;

    // 员工姓名
    @Schema(description = "姓名", example = "张三")
    private String name;

    // 联系电话
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    // 性别（男/女）
    @Schema(description = "性别", example = "男")
    private String sex;

    // 登录用户名
    @Schema(description = "用户名", example = "zhangsan")
    private String username;
}
