package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工相关接口")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     * @param employeeLoginDTO 员工登录时传递的数据模型
     * @return 统一的响应结果
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        //调用service层的登录方法:入参（接收的信息）
        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     * @return
     */
    @ApiOperation("员工登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 新增员工
     * @param employeeDTO 员工注册时传递的数据模型
     * @return 返回统一响应结果
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result<?> register(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工：{}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询
     * @param employeePageQueryDTO 分页查询员工时传递的数据模型
     * @return 返回Result对象
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("page() called with parameters => 【employeePageQueryDTO = {}】", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用或禁用账号
     * @param status 状态码
     * @param id 员工id
     * @return 返回统一响应结果
     */
    @ApiOperation("员工状态")
    @PostMapping("/status/{status}")
    public Result<?> startOrStop(@PathVariable Integer status, Long id) {
        log.info("startOrStop() called with parameters => 【status = {}】, 【id = {}】",status, id);
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    /**
     * 根据ID查询员工
     */
    @ApiOperation("查询回显")
    @GetMapping("/{id}")
    public Result<EmployeeDTO> selectByID(@PathVariable Integer id){
        log.info("selectByID() called with parameters => 【id = {}】",id);
        EmployeeDTO employeeDTO = employeeService.selectById(id);
        return Result.success(employeeDTO);
    }
    /**
     * 修改员工
     */
    @PutMapping
    @ApiOperation("编辑员工")
    public Result<?> update(@RequestBody EmployeeDTO employeeDTO){
        log.info("updateEmp() called with parameters => 【employeeDTO = {}】",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
}
