package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeInsertDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeQueryDTO;
import com.sky.dto.EmployeeUpdateDTO;
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
 * 员工管理
 * @RestController - 组合注解，等于@Controller + @ResponseBody
 *                  表示该类的所有方法返回的数据直接写入响应体，不是视图
 * @RequestMapping - 请求映射，可标注在类或方法上
 *                  用于类上表示该Controller所有接口的公共路径前缀
 * @Tag - Swagger注解，用于给接口分组，name为组名
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
     * @PostMapping - 请求方式映射，等于@RequestMapping(method = RequestMethod.POST)
     *              只处理POST请求
     * @RequestBody - 将请求体中的JSON数据绑定到方法参数上
     * @Operation - Swagger注解，用于描述接口，summary为接口摘要
     * @param dto 登录时传递的用户名和密码
     * @return 登录后返回的主键值、用户名、姓名、Jwt令牌
     */
    @Operation(summary = "员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO dto) {
        log.info("员工登录: {}", dto.getUsername());

        // 1. 调用service查询员工
        Employee employee = employeeService.login(dto);

        // 2. 登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMPLOYEE_ID, employee.getId());
        // JwtUtil.createJWT() - 生成JWT令牌
        // jwtProperties.getAdminSecretKey() - 从配置获取加密密钥
        // jwtProperties.getAdminTtl() - 从配置获取过期时间(毫秒)
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
     * 前端清除token即可，后端无需额外处理
     */
    @PostMapping("/logout")
    @Operation(summary = "员工退出")
    public Result logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param dto 新增时传递的用户名、姓名、手机号、性别、身份证号
     */
    @PostMapping
    @Operation(summary = "新增员工")
    public Result save(@RequestBody EmployeeInsertDTO dto) {
        log.info("新增员工:{}", dto.getUsername());

        employeeService.save(dto);

        return Result.success();
    }

    /**
     * 分页查询员工
     * @param dto 查询条件（姓名、页码、每页条数）
     * @return 分页结果（不含密码）
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询员工")
    public Result<PageResult<EmployeeVO>> page(EmployeeQueryDTO dto) {
        log.info("分页查询员工,查询参数:{}", dto);

        PageResult<EmployeeVO> pageResult = employeeService.page(dto);

        return Result.success(pageResult);
    }

    /**
     * 启用/禁用员工账号
     * @param status 目标状态（1启用，0禁用）
     * @param id 员工id
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "修改员工账号状态")
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("修改员工账号状态,id:{},状态:{}", id, status);

        employeeService.startOrStop(id, status);

        return Result.success();
    }

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return 员工信息（不含密码）
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工")
    public Result<EmployeeVO> getById(@PathVariable Long id) {
        log.info("根据id查询员工:{}", id);

        EmployeeVO employeeVO = employeeService.getById(id);

        return Result.success(employeeVO);
    }

    /**
     * 修改员工信息
     * @param dto 要修改的员工数据（id必填）
     */
    @PutMapping
    @Operation(summary = "修改员工信息")
    public Result update(@RequestBody EmployeeUpdateDTO dto) {
        log.info("修改员工:{}", dto.getId());

        employeeService.update(dto);

        return Result.success();
    }
}
