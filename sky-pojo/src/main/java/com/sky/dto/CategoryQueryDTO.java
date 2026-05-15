package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类分页查询DTO
 * 用于接收分页查询条件，支持按名称模糊查询和类型筛选
 */
@Data
@Schema(description = "分类分页查询参数")
public class CategoryQueryDTO implements Serializable {

    // 分类名称（模糊查询条件，可选）
    @Schema(description = "分类名称（模糊查询）", example = "川")
    private String name;

    // 页码（默认第1页）
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    // 每页记录数（默认10条）
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;

    // 分类类型（1:菜品分类 2:套餐分类，可选）
    @Schema(description = "类型（1菜品分类 2套餐分类）", example = "1")
    private Integer type;
}
