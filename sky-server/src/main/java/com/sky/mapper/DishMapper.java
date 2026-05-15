package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.DishQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishGetByIdVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜品Mapper接口
 */
@Mapper
public interface DishMapper {

    /**
     * 分页查询菜品
     *
     * @param dto 查询条件
     * @return 菜品列表
     */
    List<Dish> page(DishQueryDTO dto);

    /**
     * 动态更新菜品（非null字段才会更新）
     *
     * @param dish 菜品信息
     */
    @AutoFill(OperationType.UPDATE)
    void update(Dish dish);

    /**
     * 根据ID查询菜品详情（含分类名称和口味列表）
     *
     * @param id 菜品ID
     * @return 菜品详情
     */
    DishGetByIdVO getById(Long id);

    /**
     * 新增菜品
     *
     * @param dish 菜品信息
     */
    @AutoFill(OperationType.INSERT)
    void save(Dish dish);

    /**
     * 根据ID集合批量删除菜品
     *
     * @param ids 菜品ID集合
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据分类ID查询菜品列表
     *
     * @param id 分类ID
     * @return 菜品列表
     */
    List<Dish> getByCategoryId(Long id);
}
