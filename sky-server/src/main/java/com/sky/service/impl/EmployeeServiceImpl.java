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
import com.sky.vo.EmployeePageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * 员工登录
     *
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 统一的响应结果
     */
    @Override
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

//        //设置创建，修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        //设置当前创建人id和修改人id,从ThreadLocal
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        //执行插入
        employeeMapper.inset(employee);
    }

    /**
     * 分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //设置分页条件
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        //执行查询
        Page<Employee> pageQuery = employeeMapper.pageQuery(employeePageQueryDTO);
        //数据脱敏(取消显示密码)
//        List<EmployeePageVO> collect = pageQuery.stream().map(item -> {
//            EmployeePageVO employeePageVO = new EmployeePageVO();
//            BeanUtils.copyProperties(item, employeePageVO);
//            return employeePageVO;
//        }).collect(Collectors.toList());
        pageQuery.forEach(item -> item.setPassword("******"));
        return new PageResult(pageQuery.getTotal(), pageQuery.getResult());
    }

    /**
     * 启用或禁用员工
     *
     * @param status 状态码
     * @param id     员工id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Employee build = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(build);
    }

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @Override
    public EmployeeDTO selectById(Integer id) {
        log.info("selectById() called with parameters => 【id = {}】", id);
        //根据id查询
        Employee employee = employeeMapper.getById(id);
        //新建DTO对象
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    /**
     * 修改员工
     *
     * @param employeeDTO
     */
    @Override
    public void update(EmployeeDTO employeeDTO) {
        log.info("updateEmp() called with parameters => 【employeeDTO = {}】", employeeDTO);
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
    }
}
