package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 返回具体信息
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 新增员工
     * @param employeeDTO 员工登录时传递的数据模型
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用或禁用账号
     * @param status 状态码
     * @param id 员工id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    EmployeeDTO selectById(Integer id);

    /**
     * 修改员工
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);
}
