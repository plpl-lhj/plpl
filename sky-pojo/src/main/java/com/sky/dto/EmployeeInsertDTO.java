package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增员工DTO
 * 用于接收新增员工时的请求参数
 */
@Data
@Schema(description = "新增员工请求参数")
public class EmployeeInsertDTO implements Serializable {

    // 登录用户名（需唯一）
    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    // 员工姓名
    @Schema(description = "姓名", example = "张三")
    private String name;

    // 联系电话
    @Schema(description = "手机号", example = "13800138000")
    private String phone;

    // 性别（男/女）
    @Schema(description = "性别", example = "男")
    private String sex;

    // 身份证号码
    @Schema(description = "身份证号", example = "110101199001011234")
    private String idNumber;
}
