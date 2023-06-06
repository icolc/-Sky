package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 新增员工
     * @param employee 员工信息
     */
    void inset(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return page<Employee>
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据条件动态修改条件
     * @param build 传递的条件
     */
    void update(Employee build);

    /**
     * 根据ID查询员工
     * @param id
     * @return
     */
    Employee getById(Integer id);
}
