package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜品分页查询条件
 */
@Data
@Schema(description = "菜品分页查询条件")
public class DishQueryDTO implements Serializable {
    // 分类id（按分类筛选）
    @Schema(description = "分类id")
    private Long categoryId;

    // 菜品名称（模糊查询条件）
    @Schema(description = "菜品名称")
    private String name;

    // 页码，默认第1页
    @Schema(description = "页码")
    private Integer page = 1;

    // 每页条数，默认10条
    @Schema(description = "每页条数")
    private Integer pageSize = 10;

    // 状态（1启售，0停售）
    @Schema(description = "状态")
    private Integer status;
}
