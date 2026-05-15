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

    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
    }

    /**
     * 分页查询菜品
     * @param dto 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Dish> page(DishQueryDTO dto) {
        log.info("分页查询菜品,查询参数: {}", dto);

        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<Dish> dishList = dishMapper.page(dto);
        Page<Dish> page = (Page<Dish>) dishList;

        return new PageResult<>(page.getTotal(), page.getResult());
    }

    /**
     * 启售/停售菜品
     * @param id 菜品id
     * @param status 目标状态（1启售，0停售）
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        log.info("修改菜品状态,id: {},状态: {}", id, status);

        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        dishMapper.update(dish);
    }

    /**
     * 根据id查询菜品详情
     * @param id 菜品id
     * @return 菜品详情（含分类名称和口味列表）
     */
    @Override
    public DishGetByIdVO getById(Long id) {
        log.info("根据id查询菜品: {}", id);

        return dishMapper.getById(id);
    }

    /**
     * 新增菜品
     * @param dto 菜品信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(DishInsertDTO dto) {
        log.info("新增菜品: {}", dto.getName());

        // 1.构造菜品对象，DTO属性拷贝到实体
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        dish.setStatus(StatusConstant.DISABLE);
        // 2.调用mapper新增菜品（自动填充创建/更新时间、操作人，返回自增主键）
        dishMapper.save(dish);

        // 3.获取自增主键，设置到口味中一并保存
        Long dishId = dish.getId();
        List<DishFlavor> dishFlavorList = dto.getFlavors();
        if (!CollectionUtils.isEmpty(dishFlavorList)) {
            dishFlavorList.forEach(d -> d.setDishId(dishId));
            dishFlavorMapper.save(dishFlavorList);
        }
    }

    /**
     * 修改菜品
     * @param dto 菜品信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(DishUpdateDTO dto) {
        log.info("修改菜品: {}", dto.getId());

        // 1.DTO属性拷贝到实体
        Long dishId = dto.getId();
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);

        // 2.更新菜品基本信息（自动填充更新时间、操作人）
        dishMapper.update(dish);

        // 3.删除原口味，重新插入新口味
        dishFlavorMapper.deleteByIds(List.of(dishId));
        List<DishFlavor> dishFlavorList = dto.getFlavors();
        if (!CollectionUtils.isEmpty(dishFlavorList)) {
            dishFlavorList.forEach(d -> d.setDishId(dishId));
            dishFlavorMapper.save(dishFlavorList);
        }
    }

    /**
     * 批量删除菜品
     * @param ids 菜品id集合
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Long> ids) {
        log.info("批量删除菜品: {}", ids);

        dishMapper.deleteByIds(ids);

        dishFlavorMapper.deleteByIds(ids);
    }
}
