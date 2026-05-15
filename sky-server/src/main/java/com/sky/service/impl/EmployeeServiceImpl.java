package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.BaseConstant;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.*;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import com.sky.utils.PasswordUtil;
import com.sky.vo.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 员工服务实现类
 *
 * @Service — 标记为服务层组件，语义化的@Component
 *           Spring会自动扫描并注册到IOC容器
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;

    /**
     * 构造函数注入Mapper
     *
     * @param employeeMapper 员工Mapper接口
     */
    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 员工登录
     * 验证流程：用户名存在 → 密码正确 → 账号未禁用
     *
     * @param loginDTO 登录参数（用户名、密码）
     * @return 员工实体信息
     * @throws AccountNotFoundException 用户名不存在时抛出
     * @throws PasswordErrorException   密码错误时抛出
     * @throws AccountLockedException   账号被禁用时抛出
     */
    @Override
    public Employee login(EmployeeLoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        // 1. 根据用户名查询员工
        Employee employee = employeeMapper.getByUsername(username);
        if (employee == null) {
            log.error("账号不存在: {}", username);
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 2. 验证密码（Argon2id哈希验证）
        if (!PasswordUtil.verify(password, employee.getPassword())) {
            log.error("密码错误: {}", username);
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 3. 检查账号状态（0=禁用）
        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            log.error("账号被禁用: {}", username);
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        return employee;
    }

    /**
     * 新增员工
     * 默认密码：123456（Argon2id加密），默认状态：启用
     *
     * @param insertDTO 员工信息
     */
    @Override
    public void save(EmployeeInsertDTO insertDTO) {
        // 1. DTO → Entity
        Employee employee = new Employee();
        BeanUtils.copyProperties(insertDTO, employee);

        // 2. 设置默认密码和状态
        employee.setPassword(PasswordUtil.hash(PasswordConstant.DEFAULT_PASSWORD));
        employee.setStatus(StatusConstant.ENABLE);

        // 3. 调用Mapper插入（自动填充审计字段）
        employeeMapper.save(employee);
    }

    /**
     * 分页查询员工
     * 注意：PageHelper.startPage()必须紧挨Mapper调用
     *
     * @param queryDTO 查询条件
     * @return 分页结果（不含密码字段）
     */
    @Override
    public PageResult<EmployeeVO> page(EmployeeQueryDTO queryDTO) {
        // 1. 设置分页参数
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());

        // 2. 执行查询
        List<Employee> employeeList = employeeMapper.page(queryDTO);
        Page<Employee> pageData = (Page<Employee>) employeeList;

        // 3. Entity → VO（排除密码字段）
        List<EmployeeVO> voList = pageData.getResult().stream()
                .map(employee -> {
                    EmployeeVO employeeVO = new EmployeeVO();
                    BeanUtils.copyProperties(employee, employeeVO);
                    return employeeVO;
                })
                .toList();

        return new PageResult<>(pageData.getTotal(), voList);
    }

    /**
     * 启用/禁用员工账号
     *
     * @param employeeId 员工ID
     * @param status     目标状态（1:启用 0:禁用）
     */
    @Override
    public void startOrStop(Long employeeId, Integer status) {
        Employee employee = Employee.builder()
                .id(employeeId)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据ID查询员工
     *
     * @param employeeId 员工ID
     * @return 员工信息VO（不含密码）
     */
    @Override
    public EmployeeVO getById(Long employeeId) {
        Employee employee = employeeMapper.getById(employeeId);
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVO);
        return employeeVO;
    }

    /**
     * 修改员工信息
     *
     * @param updateDTO 员工信息（ID必填）
     */
    @Override
    public void update(EmployeeUpdateDTO updateDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(updateDTO, employee);
        employeeMapper.update(employee);
    }

    /**
     * 修改密码
     * 验证旧密码后更新为新密码
     *
     * @param passwordDTO 旧密码和新密码
     * @throws PasswordErrorException 旧密码错误时抛出
     */
    @Override
    public void updatePassword(EmployeeUpdatePasswordDTO passwordDTO) {
        // 1. 从ThreadLocal获取当前登录用户ID
        Long currentUserId = Long.valueOf(
                BaseContext.getThreadLocal().get(BaseConstant.ID).toString()
        );

        // 2. 查询当前用户信息
        Employee currentEmployee = employeeMapper.getById(currentUserId);

        // 3. 验证旧密码
        if (PasswordUtil.verify(passwordDTO.getOldPassword(), currentEmployee.getPassword())) {
            // 4. 更新为新密码
            Employee updateEntity = new Employee();
            updateEntity.setId(currentUserId);
            updateEntity.setPassword(PasswordUtil.hash(passwordDTO.getNewPassword()));
            employeeMapper.update(updateEntity);
        } else {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
    }
}
