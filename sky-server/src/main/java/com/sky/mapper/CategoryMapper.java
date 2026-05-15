package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 分类Mapper接口
 */
@Mapper
public interface CategoryMapper {
    /**
     * 新增分类
     * @param category 分类信息
     */
    @AutoFill(OperationType.INSERT)
    void save(Category category);

    /**
     * 分页查询分类（配合PageHelper使用）
     * @param dto 查询条件
     * @return 分类列表
     */
    List<Category> page(CategoryQueryDTO dto);

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    void deleteById(Long id);

    /**
     * 动态更新分类（非null字段才会更新）
     * @param category 分类信息
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    /**
     * 根据类型查询分类列表
     * @param type 类型（1菜品分类，2套餐分类）
     * @return 分类列表
     */
    List<Category> getByTypes(Integer type);
}
