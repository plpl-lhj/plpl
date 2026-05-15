package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 修改菜品时传递的数据模型
 */
@Data
@Schema(description = "修改菜品时传递的数据模型")
public class DishUpdateDTO implements Serializable {
    // 菜品id（必填，用于定位要修改的记录）
    @Schema(description = "菜品id")
    private Long id;

    // 菜品名称
    @Schema(description = "菜品名称")
    private String name;

    // 分类id
    @Schema(description = "分类id")
    private Long categoryId;

    // 价格
    @Schema(description = "价格")
    private double price;

    // 图片路径
    @Schema(description = "图片路径")
    private String image;

    // 描述
    @Schema(description = "描述")
    private String description;

    // 状态（1启售，0停售）
    @Schema(description = "状态")
    private Integer status;

    // 口味列表
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors;
}
