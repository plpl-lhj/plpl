package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.EmployeeVO;

/**
 * 员工服务接口
 * 面向接口编程，便于AOP代理和事务管理
 */
public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param dto 登录参数（用户名、密码）
     * @return 员工信息
     */
    Employee login(EmployeeLoginDTO dto);

    /**
     * 新增员工
     *
     * @param dto 员工信息
     */
    void save(EmployeeInsertDTO dto);

    /**
     * 分页查询员工
     *
     * @param dto 查询条件
     * @return 分页结果（不含密码）
     */
    PageResult<EmployeeVO> page(EmployeeQueryDTO dto);

    /**
     * 启用/禁用员工账号
     *
     * @param id     员工ID
     * @param status 目标状态（1启用，0禁用）
     */
    void startOrStop(Long id, Integer status);

    /**
     * 根据ID查询员工
     *
     * @param id 员工ID
     * @return 员工信息（不含密码）
     */
    EmployeeVO getById(Long id);

    /**
     * 修改员工信息
     *
     * @param dto 员工信息
     */
    void update(EmployeeUpdateDTO dto);

    /**
     * 修改密码
     *
     * @param dto 旧密码和新密码
     */
    void updatePassword(EmployeeUpdatePasswordDTO dto);
}
