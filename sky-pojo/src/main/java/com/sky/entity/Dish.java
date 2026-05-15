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
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish implements Serializable {
    // serialVersionUID — 序列化版本号，用于反序列化时校验版本一致性
    @Serial
    private static final long serialVersionUID = 1L;
    // 主键
    private Long id;
    // 菜品名称
    private String name;
    // 分类id
    private Long categoryId;
    // 价格
    private double price;
    // 图片路径
    private String image;
    // 描述
    private String description;
    // 状态（1启售，0停售）
    private Integer status;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
    // 创建人id
    private Long createUser;
    // 更新人id
    private Long updateUser;
}
