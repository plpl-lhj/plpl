package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品口味Mapper接口
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * 批量保存口味
     *
     * @param dishFlavorList 口味列表
     */
    void save(List<DishFlavor> dishFlavorList);

    /**
     * 根据菜品ID集合批量删除口味
     *
     * @param dishIds 菜品ID集合
     */
    void deleteByIds(List<Long> dishIds);
}
