package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.ComboService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:03
 * @description: 套餐管理页
 */
@Api(tags = "套餐相关接口")
@Slf4j
@Component
@RestController
@RequestMapping("/admin/setmeal")
public class ComboController {
    private final ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("page() called with parameters => 【setmealPageQueryDTO = {}】",setmealPageQueryDTO);
        //调用service层分页查询方法
        PageResult pageResult = comboService.selectPage(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 新增套餐
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result<?> insert(@RequestBody SetmealDTO setmealDTO){
        log.info("insert() called with parameters => 【setmealDTO = {}】",setmealDTO);
        //调用service新增方法
        comboService.insert(setmealDTO);
        return Result.success();
    }
}
