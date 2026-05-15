package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishInsertDTO;
import com.sky.dto.DishQueryDTO;
import com.sky.dto.DishUpdateDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishGetByIdVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 菜品服务实现类
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;

    /**
     * 构造函数注入Mapper
     *
     * @param dishMapper       菜品Mapper接口
     * @param dishFlavorMapper 菜品口味Mapper接口
     */
    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    /**
     * 分页查询菜品
     *
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Dish> page(DishQueryDTO queryDTO) {
        log.info("分页查询菜品: {}", queryDTO);

        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        List<Dish> dishList = dishMapper.page(queryDTO);
        Page<Dish> pageData = (Page<Dish>) dishList;

        return new PageResult<>(pageData.getTotal(), pageData.getResult());
    }

    /**
     * 启售/停售菜品
     *
     * @param dishId 菜品ID
     * @param status 目标状态（1:启售 0:停售）
     */
    @Override
    public void startOrStop(Long dishId, Integer status) {
        log.info("修改菜品状态: {}, id: {}", dishId, status);

        Dish dish = Dish.builder()
                .id(dishId)
                .status(status)
                .build();
        dishMapper.update(dish);
    }

    /**
     * 根据ID查询菜品详情
     *
     * @param dishId 菜品ID
     * @return 菜品详情（含分类名称和口味列表）
     */
    @Override
    public DishGetByIdVO getById(Long dishId) {
        log.info("根据id查询菜品: {}", dishId);
        return dishMapper.getById(dishId);
    }

    /**
     * 新增菜品
     * 事务：菜品和口味同时成功或回滚
     *
     * @param insertDTO 菜品信息（含口味列表）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(DishInsertDTO insertDTO) {
        log.info("新增菜品: {}", insertDTO.getName());

        // 1. DTO → Entity
        Dish dish = new Dish();
        BeanUtils.copyProperties(insertDTO, dish);
        dish.setStatus(StatusConstant.DISABLE);

        // 2. 新增菜品（自动填充审计字段，返回自增主键）
        dishMapper.save(dish);

        // 3. 获取自增主键，保存口味列表
        Long dishId = dish.getId();
        List<DishFlavor> flavorList = insertDTO.getFlavors();
        if (!CollectionUtils.isEmpty(flavorList)) {
            flavorList.forEach(flavor -> flavor.setDishId(dishId));
            dishFlavorMapper.save(flavorList);
        }
    }

    /**
     * 修改菜品
     * 事务：先删旧口味，再插入新口味
     *
     * @param updateDTO 菜品信息（含口味列表）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DishUpdateDTO updateDTO) {
        log.info("修改菜品: {}", updateDTO.getId());

        Long dishId = updateDTO.getId();

        // 1. 更新菜品基本信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(updateDTO, dish);
        dishMapper.update(dish);

        // 2. 删除旧口味，插入新口味
        dishFlavorMapper.deleteByIds(List.of(dishId));
        List<DishFlavor> flavorList = updateDTO.getFlavors();
        if (!CollectionUtils.isEmpty(flavorList)) {
            flavorList.forEach(flavor -> flavor.setDishId(dishId));
            dishFlavorMapper.save(flavorList);
        }
    }

    /**
     * 批量删除菜品
     * 事务：菜品和口味同时删除
     *
     * @param dishIds 菜品ID集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Long> dishIds) {
        log.info("批量删除菜品: {}", dishIds);
        dishMapper.deleteByIds(dishIds);
        dishFlavorMapper.deleteByIds(dishIds);
    }

    /**
     * 根据分类ID查询菜品列表
     *
     * @param categoryId 分类ID
     * @return 菜品列表
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        log.info("根据分类id查询菜品: {}", categoryId);
        return dishMapper.getByCategoryId(categoryId);
    }
}
