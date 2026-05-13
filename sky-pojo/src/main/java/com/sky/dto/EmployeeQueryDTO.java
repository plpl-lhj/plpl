package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工分页时传递的数据模型(员工分页查询条件)
 */
@Data
@Schema(description = "员工分页查询条件")
public class EmployeeQueryDTO implements Serializable {
    // 姓名（模糊查询条件）
    @Schema(description = "姓名")
    private String name;

    // 页码，默认第1页
    @Schema(description = "页码")
    private Integer page = 1;

    // 每页条数，默认10条
    @Schema(description = "每页条数")
    private Integer pageSize = 10;
}
