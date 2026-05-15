package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增分类DTO
 * 用于接收新增分类时的请求参数
 */
@Data
@Schema(description = "新增分类请求参数")
public class CategoryInsertDTO implements Serializable {

    // 分类名称
    @Schema(description = "分类名称", example = "川菜")
    private String name;

    // 排序号（数值越小越靠前）
    @Schema(description = "排序号", example = "1")
    private Integer sort;

    // 分类类型（1:菜品分类 2:套餐分类）
    @Schema(description = "类型（1菜品分类 2套餐分类）", example = "1")
    private Integer type;
}
