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
 * 菜品详情VO，包含分类名称和口味列表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "菜品详情VO")
public class DishGetByIdVO implements Serializable {
    // serialVersionUID — 序列化版本号，用于反序列化时校验版本一致性
    @Serial
    private static final long serialVersionUID = 1L;

    // 菜品id
    @Schema(description = "菜品id")
    private Long id;

    // 菜品名称
    @Schema(description = "菜品名称")
    private String name;

    // 分类id
    @Schema(description = "分类id")
    private Long categoryId;

    // 分类名称
    @Schema(description = "分类名称")
    private String categoryName;

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

    // 更新时间
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 口味列表
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors;
}
