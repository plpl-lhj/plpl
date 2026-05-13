package com.sky.server;

import com.sky.dto.EmployeeInstantDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeQueryDTO;
import com.sky.dto.EmployeeUpdateDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.vo.EmployeeVO;

/**
 * 员工服务接口
 * 使用接口的好处：
 * 1. 面向接口编程，代码更灵活
 * 2. 便于实现AOP、事务管理等
 * 3. 便于使用动态代理创建代理对象
 */
public interface EmployeeService {
    /**
     * 员工登录
     * @param dto 登录时传递的用户名和密码
     * @return 返回员工信息
     */
    Employee login(EmployeeLoginDTO dto);

    /**
     * 新增员工
     * @param dto 新增时传递的用户名、姓名、手机号、性别、身份证号
     */
    void save(EmployeeInstantDTO dto);

    /**
     * 分页查询员工
     * @param dto 查询条件（姓名、页码、每页条数）
     * @return 分页结果（总记录数 + 当前页数据，不含密码）
     */
    PageResult<EmployeeVO> page(EmployeeQueryDTO dto);

    /**
     * 启用/禁用员工账号
     * @param id 员工id
     * @param status 目标状态（1启用，0禁用）
     */
    void startOrStop(Long id, Integer status);

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return 员工信息（不含密码）
     */
    EmployeeVO getById(Long id);

    /**
     * 修改员工信息
     * @param dto 要修改的员工数据
     */
    void update(EmployeeUpdateDTO dto);
}