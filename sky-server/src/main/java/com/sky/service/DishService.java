package com.sky.service;

import com.sky.dto.DishInsertDTO;
import com.sky.dto.DishQueryDTO;
import com.sky.dto.DishUpdateDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishGetByIdVO;

import java.util.List;

/**
 * 菜品服务接口
 */
public interface DishService {
    /**
     * 分页查询菜品
     * @param dto 查询条件
     * @return 分页结果
     */
    PageResult<Dish> page(DishQueryDTO dto);

    /**
     * 启售/停售菜品
     * @param id 菜品id
     * @param status 目标状态（1启售，0停售）
     */
    void startOrStop(Long id, Integer status);

    /**
     * 根据id查询菜品详情
     * @param id 菜品id
     * @return 菜品详情（含分类名称和口味列表）
     */
    DishGetByIdVO getById(Long id);

    /**
     * 新增菜品
     * @param dto 菜品信息
     * @return 新增菜品的id
     */
    void save(DishInsertDTO dto);

    /**
     * 修改菜品
     * @param dto 菜品信息
     */
    void update(DishUpdateDTO dto);

    /**
     * 批量删除菜品
     * @param ids 菜品id集合
     */
    void deleteByIds(List<Long> ids);
}
