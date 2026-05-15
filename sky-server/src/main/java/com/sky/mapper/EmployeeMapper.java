package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeeQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工Mapper接口
 * MyBatis会自动创建代理对象，方法名对应XML中的statement id
 *
 * @Mapper — 标记为Mapper接口，MyBatis自动扫描并创建代理实现
 */
@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username 用户名
     * @return 员工信息（不存在返回null）
     */
    Employee getByUsername(String username);

    /**
     * 新增员工
     *
     * @param employee 员工信息
     */
    @AutoFill(OperationType.INSERT)
    void save(Employee employee);

    /**
     * 分页查询员工（配合PageHelper）
     *
     * @param dto 查询条件
     * @return 员工列表
     */
    List<Employee> page(EmployeeQueryDTO dto);

    /**
     * 动态更新员工信息（非null字段才会更新）
     *
     * @param employee 员工信息
     */
    @AutoFill(OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据ID查询员工
     *
     * @param id 员工ID
     * @return 员工信息
     */
    Employee getById(Long id);
}
