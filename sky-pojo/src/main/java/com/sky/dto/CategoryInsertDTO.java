package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 新增分类时传递的数据模型
 */
@Data
@Schema(description = "新增分类时传递的数据模型")
public class CategoryInsertDTO implements Serializable {
    // 分类名称
    @Schema(description = "分类名称")
    private String name;

    // 排序号
    @Schema(description = "排序号")
    private Integer sort;

    // 类型（1菜品分类，2套餐分类）
    @Schema(description = "类型")
    private Integer type;
}
