package com.sky.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜品口味实体类
 * 对应数据库dish_flavor表，存储菜品的口味选项（如辣度、甜度）
 */
@Data
public class DishFlavor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 主键ID（数据库自增）
    private Long id;

    // 所属菜品ID（关联dish.id）
    private Long dishId;

    // 口味名称（如"辣度"、"甜度"、"温度"）
    private String name;

    // 口味选项值（如"微辣/中辣/特辣"）
    private String value;
}
