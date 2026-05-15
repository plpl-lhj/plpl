package com.sky.vo;

import com.sky.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜品详情VO
 * 用于返回菜品完整信息（含分类名称和口味列表）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜品详情响应数据")
public class DishGetByIdVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 菜品ID
    @Schema(description = "菜品ID")
    private Long id;

    // 菜品名称
    @Schema(description = "菜品名称")
    private String name;

    // 所属分类ID
    @Schema(description = "分类ID")
    private Long categoryId;

    // 所属分类名称（关联查询category.name）
    @Schema(description = "分类名称")
    private String categoryName;

    // 菜品价格（单位：元）
    @Schema(description = "价格")
    private double price;

    // 菜品图片URL
    @Schema(description = "图片路径")
    private String image;

    // 菜品描述
    @Schema(description = "描述")
    private String description;

    // 上架状态（1:启售 0:停售）
    @Schema(description = "状态")
    private Integer status;

    // 记录最后更新时间
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 口味列表（关联查询dish_flavor）
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors;
}
