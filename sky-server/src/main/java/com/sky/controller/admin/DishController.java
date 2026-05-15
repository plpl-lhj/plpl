package com.sky.controller.admin;

import com.sky.dto.DishInsertDTO;
import com.sky.dto.DishQueryDTO;
import com.sky.dto.DishUpdateDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishGetByIdVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Tag(name = "菜品管理")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * 分页查询菜品
     * @param dto 查询条件
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询菜品")
    public Result<PageResult<Dish>> page(DishQueryDTO dto) {
        log.info("分页查询菜品,查询参数: {}", dto);

        PageResult<Dish> pageResult = dishService.page(dto);

        return Result.success(pageResult);
    }

    /**
     * 启售/停售菜品
     * @param status 目标状态（1启售，0停售）
     * @param id 菜品id
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启售/停售菜品")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改菜品状态,id: {},状态: {}", id, status);

        dishService.startOrStop(id, status);

        return Result.success();
    }

    /**
     * 根据id查询菜品详情
     * @param id 菜品id
     * @return 菜品详情（含分类名称和口味列表）
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询菜品")
    public Result<DishGetByIdVO> getById(@PathVariable Long id) {
        log.info("根据id查询菜品: {}", id);

        DishGetByIdVO vo = dishService.getById(id);

        return Result.success(vo);
    }

    /**
     * 新增菜品
     * @param dto 菜品信息（含口味列表）
     * @return 新增菜品的id
     */
    @PostMapping
    @Operation(summary = "新增菜品")
    public Result save(@RequestBody DishInsertDTO dto) {
        log.info("新增菜品: {}", dto.getName());

        dishService.save(dto);

        return Result.success();
    }

    /**
     * 修改菜品
     * @param dto 菜品信息（含口味列表）
     */
    @PutMapping
    @Operation(summary = "修改菜品")
    public Result update(@RequestBody DishUpdateDTO dto) {
        log.info("修改菜品: {}", dto.getId());

        dishService.update(dto);

        return Result.success();
    }

    /**
     * 批量删除菜品
     * @param ids 菜品id集合
     */
    @DeleteMapping
    @Operation(summary = "批量删除菜品")
    public Result deleteByIds(@RequestParam List<Long> ids) {
        log.info("批量删除菜品: {}", ids);

        dishService.deleteByIds(ids);

        return Result.success();
    }
}
