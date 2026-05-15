package com.sky.service;

import com.sky.dto.CategoryInsertDTO;
import com.sky.dto.CategoryQueryDTO;
import com.sky.dto.CategoryUpdateDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {
    /**
     * 新增分类
     * @param dto 分类信息
     */
    void save(CategoryInsertDTO dto);

    /**
     * 分页查询分类
     * @param dto 查询条件
     * @return 分页结果
     */
    PageResult<Category> page(CategoryQueryDTO dto);

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    void deleteById(Long id);

    /**
     * 启用/禁用分类
     * @param id 分类id
     * @param status 目标状态（1启用，0禁用）
     */
    void startOrStop(Long id, Integer status);

    /**
     * 修改分类
     * @param dto 要修改的分类数据
     */
    void update(CategoryUpdateDTO dto);

    /**
     * 根据类型查询分类列表
     * @param type 类型（1菜品分类，2套餐分类）
     * @return 分类列表
     */
    List<Category> getByTypes(Integer type);
}
