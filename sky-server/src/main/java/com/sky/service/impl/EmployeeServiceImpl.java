package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.EmployeeInsertDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeeQueryDTO;
import com.sky.dto.EmployeeUpdateDTO;
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
 * @Service — 将该类标记为服务层组件，会被Spring扫描并注册到容器中
 *           与@Component功能相同，但语义更明确
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 员工登录
     * @param dto 登录时传递的用户名和密码
     * @return 返回员工信息
     */
    @Override
    public Employee login(EmployeeLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        // 1. 根据用户名查询数据库中的员工信息
        // employeeMapper.getByUsername() - 调用MyBatis Mapper查询数据
        Employee employee = employeeMapper.getByUsername(username);
        if (employee == null) {
            // 账号不存在
            log.error("账号不存在: {}", username);
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        // 2. 密码比对
        // PasswordUtil.verify() - 解析哈希字符串中的参数进行验证
        // password - 原始密码
        // employee.getPassword() - 哈希后的密码
        if (!PasswordUtil.verify(password, employee.getPassword())) {
            // 密码错误
            log.error("密码错误: {}", username);
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 3. 检查账号状态(是否被禁用)
        // StatusConstant.DISABLE == 0 表示账号被禁用
        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            // 账号被禁用
            log.error("账号被禁用: {}", username);
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        // 4. 返回员工信息
        return employee;
    }

    /**
     * 新增员工
     * @param dto 新增时传递的用户名、姓名、手机号、性别、身份证号
     */
    @Override
    public void save(EmployeeInsertDTO dto) {
        // 1.构造员工对象，DTO属性拷贝到实体
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);

        // 2.补充默认字段：默认密码、启用状态
        employee.setPassword(PasswordUtil.hash(PasswordConstant.DEFAULT_PASSWORD));
        employee.setStatus(StatusConstant.ENABLE);

        // 3.调用mapper新增员工（自动填充创建/更新时间、操作人）
        employeeMapper.save(employee);
    }

    /**
     * 分页查询员工
     * @param dto 查询条件（姓名、页码、每页条数）
     * @return 分页结果（总记录数 + 当前页数据，不含密码）
     */
    @Override
    public PageResult<EmployeeVO> page(EmployeeQueryDTO dto) {
        // 1.PageHelper.startPage() 必须紧挨着Mapper查询方法，中间不能有其他查询
        PageHelper.startPage(dto.getPage(), dto.getPageSize());

        // 2.调用mapper得到查询后的结果
        List<Employee> employeeList = employeeMapper.page(dto);

        // 3.PageHelper返回的List实际是Page类型，强转后可获取总记录数
        Page<Employee> page = (Page<Employee>) employeeList;

        // 4.将Employee转换为EmployeeVO（不含密码）
        List<EmployeeVO> voList = page.getResult().stream().map(e -> {
            EmployeeVO vo = new EmployeeVO();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).toList();

        return new PageResult<>(page.getTotal(), voList);
    }

    /**
     * 启用/禁用员工账号
     * @param id 员工id
     * @param status 目标状态（1启用，0禁用）
     */
    @Override
    public void startOrStop(Long id, Integer status) {
        // 1.将修改的员工id和状态封装
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();

        // 2.调用mapper修改账号状态
        employeeMapper.update(employee);
    }

    /**
     * 根据id查询员工
     * @param id 员工id
     * @return 员工信息（不含密码）
     */
    @Override
    public EmployeeVO getById(Long id) {
        // 1.调用mapper返回员工信息
        Employee employee = employeeMapper.getById(id);

        // 2.转换为VO（不含密码）
        EmployeeVO vo = new EmployeeVO();
        BeanUtils.copyProperties(employee, vo);

        return vo;
    }

    /**
     * 修改员工信息
     * @param dto 要修改的员工数据
     */
    @Override
    public void update(EmployeeUpdateDTO dto) {
        // 1.DTO属性拷贝到实体
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);

        // 2.调用mapper动态更新（自动填充更新时间、操作人）
        employeeMapper.update(employee);
    }
}
