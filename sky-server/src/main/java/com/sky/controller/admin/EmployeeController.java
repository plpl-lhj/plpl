package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeInsertDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeQueryDTO;
import com.sky.dto.EmployeeUpdateDTO;
import com.sky.dto.EmployeeUpdatePasswordDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import com.sky.vo.EmployeeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理Controller
 *
 * @RestController  — 组合注解，等于@Controller + @ResponseBody，返回值直接写入响应体
 * @RequestMapping — 请求映射前缀，该Controller下所有接口的公共路径
 * @Tag            — Swagger注解，接口分组标签
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name = "员工管理")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final JwtProperties jwtProperties;

    public EmployeeController(EmployeeService employeeService, JwtProperties jwtProperties) {
        this.employeeService = employeeService;
        this.jwtProperties = jwtProperties;
    }

    /**
     * 员工登录
     *
     * @param dto 登录参数（用户名、密码）
     * @return 登录结果（用户信息 + JWT令牌）
     */
    @Operation(summary = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO dto) {
        log.info("员工登录: {}", dto.getUsername());

        // 1. 调用Service验证登录
        Employee employee = employeeService.login(dto);

        // 2. 生成JWT令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMPLOYEE_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        // 3. 构造返回结果
        EmployeeLoginVO vo = EmployeeLoginVO.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(vo);
    }

    /**
     * 员工退出
     */
    @Operation(summary = "员工退出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     *
     * @param dto 员工信息
     */
    @Operation(summary = "新增员工")
    @PostMapping
    public Result<Void> save(@RequestBody EmployeeInsertDTO dto) {
        log.info("新增员工: {}", dto.getUsername());
        employeeService.save(dto);
        return Result.success();
    }

    /**
     * 分页查询员工
     *
     * @param dto 查询条件
     * @return 分页结果
     */
    @Operation(summary = "分页查询员工")
    @GetMapping("/page")
    public Result<PageResult<EmployeeVO>> page(EmployeeQueryDTO dto) {
        log.info("分页查询员工: {}", dto);
        PageResult<EmployeeVO> pageResult = employeeService.page(dto);
        return Result.success(pageResult);
    }

    /**
     * 启用/禁用员工账号
     *
     * @param status 目标状态（1启用，0禁用）
     * @param id     员工ID
     */
    @Operation(summary = "修改员工账号状态")
    @PostMapping("/status/{status}")
    public Result<Void> startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改员工账号状态: {}, id: {}", id, status);
        employeeService.startOrStop(id, status);
        return Result.success();
    }

    /**
     * 根据ID查询员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @Operation(summary = "根据id查询员工")
    @GetMapping("/{id}")
    public Result<EmployeeVO> getById(@PathVariable Long id) {
        log.info("根据id查询员工: {}", id);
        EmployeeVO employeeVO = employeeService.getById(id);
        return Result.success(employeeVO);
    }

    /**
     * 修改员工信息
     *
     * @param dto 员工信息（ID必填）
     */
    @Operation(summary = "修改员工信息")
    @PutMapping
    public Result<Void> update(@RequestBody EmployeeUpdateDTO dto) {
        log.info("修改员工: {}", dto.getId());
        employeeService.update(dto);
        return Result.success();
    }

    /**
     * 修改密码
     *
     * @param dto 旧密码和新密码
     */
    @Operation(summary = "修改密码")
    @PutMapping("/editPassword")
    public Result<Void> updatePassword(@RequestBody EmployeeUpdatePasswordDTO dto) {
        log.info("修改密码");
        employeeService.updatePassword(dto);
        return Result.success();
    }
}
