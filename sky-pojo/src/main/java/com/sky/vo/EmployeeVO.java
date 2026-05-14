package com.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工信息返回的数据格式（不含密码）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "员工信息返回的数据格式")
public class EmployeeVO implements Serializable {
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

    // 手机号
    @Schema(description = "手机号")
    private String phone;

    // 性别
    @Schema(description = "性别")
    private String sex;

    // 身份证号
    @Schema(description = "身份证号")
    private String idNumber;

    // 账号状态(1:启用, 0:禁用)
    @Schema(description = "账号状态")
    private Integer status;

    // 创建时间
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 更新时间
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人id
    @Schema(description = "创建人id")
    private Long createUser;

    // 更新人id
    @Schema(description = "更新人id")
    private Long updateUser;
}
