package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工分页查询DTO
 * 用于接收分页查询条件，支持按姓名模糊查询
 */
@Data
@Schema(description = "员工分页查询参数")
public class EmployeeQueryDTO implements Serializable {

    // 姓名（模糊查询条件，可选）
    @Schema(description = "姓名（模糊查询）", example = "张")
    private String name;

    // 页码（默认第1页）
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    // 每页记录数（默认10条）
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;
}
