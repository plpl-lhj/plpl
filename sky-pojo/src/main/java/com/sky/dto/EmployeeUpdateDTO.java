package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改员工时传递的数据模型
 */
@Data
@Schema(description = "修改员工时传递的数据模型")
public class EmployeeUpdateDTO implements Serializable {
    // 员工id（必填，用于定位要修改的记录）
    @Schema(description = "员工id")
    private Long id;

    // 身份证号
    @Schema(description = "身份证号")
    private String idNumber;

    // 姓名
    @Schema(description = "姓名")
    private String name;

    // 手机号
    @Schema(description = "手机号")
    private String phone;

    // 性别
    @Schema(description = "性别")
    private String sex;

    // 用户名
    @Schema(description = "用户名")
    private String username;
}
