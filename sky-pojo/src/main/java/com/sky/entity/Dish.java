package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品实体类
 * 对应数据库dish表，存储菜品基本信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 主键ID（数据库自增）
    private Long id;

    // 菜品名称
    private String name;

    // 所属分类ID（关联category.id）
    private Long categoryId;

    // 菜品价格（单位：元）
    private double price;

    // 菜品图片URL
    private String image;

    // 菜品描述
    private String description;

    // 上架状态（1:启售 0:停售）
    private Integer status;

    // 记录创建时间（自动填充）
    private LocalDateTime createTime;

    // 记录最后更新时间（自动填充）
    private LocalDateTime updateTime;

    // 创建人ID（关联employee.id）
    private Long createUser;

    // 最后更新人ID（关联employee.id）
    private Long updateUser;
}
