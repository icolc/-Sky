package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 返回具体信息
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

}
