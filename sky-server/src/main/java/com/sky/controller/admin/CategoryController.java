package com.sky.controller.admin;

import com.sky.dto.CategoryInsertDTO;
import com.sky.dto.CategoryQueryDTO;
import com.sky.dto.CategoryUpdateDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理Controller
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "分类管理")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 新增分类
     *
     * @param dto 分类信息
     */
    @Operation(summary = "新增分类")
    @PostMapping
    public Result<Void> save(@RequestBody CategoryInsertDTO dto) {
        log.info("新增分类: {}", dto.getName());
        categoryService.save(dto);
        return Result.success();
    }

    /**
     * 分页查询分类
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询分类")
    @GetMapping("/page")
    public Result<PageResult<Category>> page(CategoryQueryDTO dto) {
        log.info("分页查询分类: {}", dto);
        PageResult<Category> pageResult = categoryService.page(dto);
        return Result.success(pageResult);
    }

    /**
     * 根据ID删除分类
     *
     * @param id 分类ID
     */
    @Operation(summary = "根据id删除分类")
    @DeleteMapping
    public Result<Void> delete(Long id) {
        log.info("删除分类: {}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 启用/禁用分类
     *
     * @param status 目标状态（1启用，0禁用）
     * @param id     分类ID
     */
    @Operation(summary = "修改分类状态")
    @PostMapping("/status/{status}")
    public Result<Void> startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改分类状态: {}, id: {}", id, status);
        categoryService.startOrStop(id, status);
        return Result.success();
    }

    /**
     * 修改分类
     *
     * @param dto 分类信息（ID必填）
     */
    @Operation(summary = "修改分类")
    @PutMapping
    public Result<Void> update(@RequestBody CategoryUpdateDTO dto) {
        log.info("修改分类: {}", dto.getId());
        categoryService.update(dto);
        return Result.success();
    }

    /**
     * 根据类型查询分类列表
     *
     * @param type 类型（1菜品分类，2套餐分类）
     * @return 分类列表
     */
    @Operation(summary = "根据类型查询分类列表")
    @GetMapping("/list")
    public Result<List<Category>> getByTypes(Integer type) {
        log.info("根据类型查询分类: {}", type);
        List<Category> categoryList = categoryService.getByTypes(type);
        return Result.success(categoryList);
    }
}
