package com.sky.server.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryInsertDTO;
import com.sky.dto.CategoryQueryDTO;
import com.sky.dto.CategoryUpdateDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.server.CategoryService;
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

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 新增分类
     * @param dto 分类信息
     */
    @Override
    public void save(CategoryInsertDTO dto) {
        log.info("新增分类: {}", dto.getName());

        // 1.构造分类对象，DTO属性拷贝到实体
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);

        // 2.补充默认状态（新增时默认禁用）
        category.setStatus(StatusConstant.DISABLE);

        // 3.调用mapper新增分类（自动填充创建/更新时间、操作人）
        categoryMapper.save(category);
    }

    /**
     * 分页查询分类
     * @param dto 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Category> page(CategoryQueryDTO dto) {
        log.info("分页查询分类,查询参数:{}", dto);

        // 1.PageHelper.startPage() 必须紧挨着Mapper查询方法
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        // 2.调用mapper得到查询结果
        List<Category> categoryList = categoryMapper.page(dto);

        // 3.PageHelper返回的List实际是Page类型，强转后可获取总记录数
        Page<Category> page = (Page<Category>) categoryList;

        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    @Override
    public void deleteById(Long id) {
        log.info("删除分类: {}", id);

        categoryMapper.deleteById(id);
    }

    /**
     * 启用/禁用分类
     * @param id 分类id
     * @param status 目标状态（1启用，0禁用）
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        log.info("修改分类状态,id:{},状态:{}", id, status);

        // 1.将修改的分类id和状态封装
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();

        // 2.调用mapper修改状态
        categoryMapper.update(category);
    }

    /**
     * 修改分类
     * @param dto 要修改的分类数据
     */
    @Override
    public void update(CategoryUpdateDTO dto) {
        log.info("修改分类: {}", dto.getId());

        // 1.DTO属性拷贝到实体
        Category category = new Category();
        BeanUtils.copyProperties(dto, category);

        // 2.调用mapper动态更新（自动填充更新时间、操作人）
        categoryMapper.update(category);
    }
}
