package com.sky.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改分类DTO
 * 用于接收修改分类时的请求参数（ID必填）
 */
@Data
@Schema(description = "修改分类请求参数")
public class CategoryUpdateDTO implements Serializable {

    // 分类ID（必填，用于定位要修改的记录）
    @Schema(description = "分类ID", example = "1")
    private Long id;

    // 分类名称
    @Schema(description = "分类名称", example = "川菜")
    private String name;

    // 排序号（数值越小越靠前）
    @Schema(description = "排序号", example = "1")
    private Integer sort;
}
