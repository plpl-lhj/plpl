package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增员工时传递的数据模型
 */
@Data
@Schema(description = "新增员工时传递的数据模型")
public class EmployeeInsertDTO implements Serializable {
    // 用户名
    @Schema(description = "用户名")
    private String username;

    // 姓名
    @Schema(description = "姓名")
    private String name;

    // 手机号
    @Schema(description = "手机号")
    private String phone;

    // 性别
    @Schema(description = "性别")
    private String sex;

    // 身份证号
    @Schema(description = "身份证号")
    private String idNumber;
}
