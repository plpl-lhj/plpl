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
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Serializable {
    // serialVersionUID — 序列化版本号，用于反序列化时校验版本一致性
    @Serial
    private static final long serialVersionUID = 1L;
    // 主键
    private Long id;
    // 类型（1菜品分类，2套餐分类）
    private Integer type;
    // 分类名称
    private String name;
    // 排序号
    private Integer sort;
    // 状态（1启用，0禁用）
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
