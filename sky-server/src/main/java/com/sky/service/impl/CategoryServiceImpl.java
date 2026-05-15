package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryInsertDTO;
import com.sky.dto.CategoryQueryDTO;
import com.sky.dto.CategoryUpdateDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现类
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 构造函数注入Mapper
     *
     * @param categoryMapper 分类Mapper接口
     */
    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 新增分类
     * 默认状态：禁用（需手动启用）
     *
     * @param insertDTO 分类信息
     */
    @Override
    public void save(CategoryInsertDTO insertDTO) {
        log.info("新增分类: {}", insertDTO.getName());

        // 1. DTO → Entity
        Category category = new Category();
        BeanUtils.copyProperties(insertDTO, category);

        // 2. 设置默认状态
        category.setStatus(StatusConstant.DISABLE);

        // 3. 调用Mapper插入（自动填充审计字段）
        categoryMapper.save(category);
    }

    /**
     * 分页查询分类
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Category> page(CategoryQueryDTO queryDTO) {
        log.info("分页查询分类: {}", queryDTO);

        // 1. 设置分页参数
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

        // 2. 执行查询
        List<Category> categoryList = categoryMapper.page(queryDTO);
        Page<Category> pageData = (Page<Category>) categoryList;

        return new PageResult<>(pageData.getTotal(), pageData.getResult());
    }

    /**
     * 根据ID删除分类
     *
     * @param categoryId 分类ID
     */
    @Override
    public void deleteById(Long categoryId) {
        log.info("删除分类: {}", categoryId);
        categoryMapper.deleteById(categoryId);
    }

    /**
     * 启用/禁用分类
     *
     * @param categoryId 分类ID
     * @param status     目标状态（1:启用 0:禁用）
     */
    @Override
    public void startOrStop(Long categoryId, Integer status) {
        log.info("修改分类状态: {}, id: {}", categoryId, status);

        Category category = Category.builder()
                .id(categoryId)
                .status(status)
                .build();
        categoryMapper.update(category);
    }

    /**
     * 修改分类
     *
     * @param updateDTO 分类信息（ID必填）
     */
    @Override
    public void update(CategoryUpdateDTO updateDTO) {
        log.info("修改分类: {}", updateDTO.getId());

        Category category = new Category();
        BeanUtils.copyProperties(updateDTO, category);
        categoryMapper.update(category);
    }

    /**
     * 根据类型查询分类列表
     *
     * @param type 类型（1:菜品分类 2:套餐分类）
     * @return 分类列表
     */
    @Override
    public List<Category> getByTypes(Integer type) {
        log.info("根据类型查询分类列表: {}", type);
        return categoryMapper.getByTypes(type);
    }
}
