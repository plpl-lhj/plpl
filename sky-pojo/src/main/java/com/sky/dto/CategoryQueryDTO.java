package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类分页查询条件
 */
@Data
@Schema(description = "分类分页查询条件")
public class CategoryQueryDTO implements Serializable {
    // 分类名称（模糊查询条件）
    @Schema(description = "分类名称")
    private String name;

    // 页码，默认第1页
    @Schema(description = "页码")
    private Integer page = 1;

    // 每页条数，默认10条
    @Schema(description = "每页条数")
    private Integer pageSize = 10;

    // 类型（1菜品分类，2套餐分类）
    @Schema(description = "类型")
    private Integer type;
}
