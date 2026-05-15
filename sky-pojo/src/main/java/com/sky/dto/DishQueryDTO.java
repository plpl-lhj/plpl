package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜品分页查询DTO
 * 用于接收分页查询条件，支持按分类、名称、状态筛选
 */
@Data
@Schema(description = "菜品分页查询参数")
public class DishQueryDTO implements Serializable {

    // 分类ID（按分类筛选，可选）
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    // 菜品名称（模糊查询条件，可选）
    @Schema(description = "菜品名称（模糊查询）", example = "鸡")
    private String name;

    // 页码（默认第1页）
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    // 每页记录数（默认10条）
    @Schema(description = "每页条数", example = "10")
    private Integer pageSize = 10;

    // 上架状态（1:启售 0:停售，可选）
    @Schema(description = "状态（1启售 0停售）", example = "1")
    private Integer status;
}
