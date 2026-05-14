package com.sky.mapper;

import com.sky.dto.EmployeeQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工mapper接口
 * @Mapper - MyBatis注解，标记该接口为Mapper接口
 *           MyBatis会自动创建该接口的代理对象
 * MyBatis Mapper接口说明：
 * - 不需要写实现类
 * - 方法名对应SQL映射文件中的statement id
 * - 参数和返回值会自动处理
 */
@Mapper
public interface EmployeeMapper {
    /**
     * 根据用户名查询员工
     * @param username 用户名
     * @return 返回员工信息(如果不存在返回null)
     */
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param employee 员工信息
     */
    void save(Employee employee);

    /**
     * 分页查询员工（配合PageHelper使用）
     * @param dto 查询条件
     * @return 员工列表
     */
    List<Employee> page(EmployeeQueryDTO dto);

    /**
     * 动态更新员工信息（非null字段才会更新）
     * @param employee 员工信息
     */
    void update(Employee employee);

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return 员工信息
     */
    Employee getById(Long id);
}
