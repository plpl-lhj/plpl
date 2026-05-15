package com.sky.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜品口味实体类
 */
@Data
public class DishFlavor implements Serializable {
    // serialVersionUID — 序列化版本号，用于反序列化时校验版本一致性
    @Serial
    private static final long serialVersionUID = 1L;
    // 主键
    private Long id;
    // 所属菜品id
    private Long dishId;
    // 口味名称（如"甜度"）
    private String name;
    // 口味值（如"正常冰/少冰"）
    private String value;
}
