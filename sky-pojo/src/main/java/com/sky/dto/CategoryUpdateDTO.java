package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改分类时传递的数据模型
 */
@Data
@Schema(description = "修改分类时传递的数据模型")
public class CategoryUpdateDTO implements Serializable {
    // 分类id（必填，用于定位要修改的记录）
    @Schema(description = "分类id")
    private Long id;

    // 分类名称
    @Schema(description = "分类名称")
    private String name;

    // 排序号
    @Schema(description = "排序号")
    private Integer sort;
}
