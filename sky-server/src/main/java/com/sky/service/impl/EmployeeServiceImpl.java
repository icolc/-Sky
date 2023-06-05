package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.RegexConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.*;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 员工登录
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 统一的响应结果
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);
        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对
        //MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //账号被锁定
        if (Objects.equals(employee.getStatus(), StatusConstant.DISABLE)) {
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * 新增员工
     *
     * @param employeeDTO 员工登录时传递的数据模型
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {
        //手机号检验
        if (!employeeDTO.getPhone().matches(RegexConstant.PHONE_REGEX)) {
            throw new PhoneIsErrorException(MessageConstant.PHONE_FORMAT_EXCEPTION);
        }
        //身份证检验
        if (!employeeDTO.getIdNumber().matches(RegexConstant.ID_NUMBER_REGEX)) {
            throw new IdNumberFormatException(MessageConstant.ID_NUMBER_FORMAT_EXCEPTION);
        }

        Employee employee = new Employee();
        //使用工具类进行对象属性拷贝
        BeanUtils.copyProperties(employeeDTO, employee);

        //修改其他默认值
        //设置账号状态，默认1正常,0锁定
        employee.setStatus(StatusConstant.ENABLE);

        //设置默认密码（记住，使用MD5加密）DigestUtils.md5DigestAsHex
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        //设置创建，修改时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        //设置当前创建人id和修改人id,从ThreadLocal
        // TODO:后期需要更改为当前登录人的id,已解决
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        //执行插入
        employeeMapper.inset(employee);
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> pageQuery = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult(pageQuery.getTotal(), pageQuery.getResult());
    }

}
