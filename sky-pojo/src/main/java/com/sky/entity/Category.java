package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类实体类
 * 对应数据库category表，支持菜品分类和套餐分类两种类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 主键ID（数据库自增）
    private Long id;

    // 分类类型（1:菜品分类 2:套餐分类）
    private Integer type;

    // 分类名称
    private String name;

    // 排序号（数值越小越靠前）
    private Integer sort;

    // 启用状态（1:启用 0:禁用）
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
