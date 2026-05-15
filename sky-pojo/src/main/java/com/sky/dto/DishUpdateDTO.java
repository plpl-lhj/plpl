package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 修改菜品DTO
 * 用于接收修改菜品时的请求参数（ID必填，含口味列表）
 */
@Data
@Schema(description = "修改菜品请求参数")
public class DishUpdateDTO implements Serializable {

    // 菜品ID（必填，用于定位要修改的记录）
    @Schema(description = "菜品ID", example = "1")
    private Long id;

    // 菜品名称
    @Schema(description = "菜品名称", example = "宫保鸡丁")
    private String name;

    // 所属分类ID
    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    // 菜品价格（单位：元）
    @Schema(description = "价格", example = "38.0")
    private double price;

    // 菜品图片URL
    @Schema(description = "图片路径", example = "https://xxx.jpg")
    private String image;

    // 菜品描述
    @Schema(description = "描述", example = "经典川菜")
    private String description;

    // 上架状态（1:启售 0:停售）
    @Schema(description = "状态（1启售 0停售）", example = "1")
    private Integer status;

    // 口味列表（如辣度、甜度等）
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors;
}
