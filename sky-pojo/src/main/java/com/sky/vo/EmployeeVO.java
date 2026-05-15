package com.sky.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工信息VO
 * 用于返回员工详细信息（不含密码字段）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "员工信息响应数据")
public class EmployeeVO implements Serializable {

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

    // 联系电话
    @Schema(description = "手机号")
    private String phone;

    // 性别
    @Schema(description = "性别")
    private String sex;

    // 身份证号码
    @Schema(description = "身份证号")
    private String idNumber;

    // 账号状态（1:启用 0:禁用）
    @Schema(description = "账号状态")
    private Integer status;

    // 记录创建时间
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 记录最后更新时间
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人ID
    @Schema(description = "创建人ID")
    private Long createUser;

    // 最后更新人ID
    @Schema(description = "更新人ID")
    private Long updateUser;
}
