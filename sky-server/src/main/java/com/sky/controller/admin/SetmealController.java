package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class SetmealController {
    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /**
     * 分页查询
     *
     */
    @ApiOperation("分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO) {
        log.info("page() called with parameters => 【setmealPageQueryDTO = {}】", setmealPageQueryDTO);
        //调用service层分页查询方法
        PageResult pageResult = setmealService.selectPage(setmealPageQueryDTO);

        return Result.success(pageResult);
    }

    /**
     * 新增套餐
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result<?> insert(@RequestBody SetmealDTO setmealDTO) {
        log.info("insert() called with parameters => 【setmealDTO = {}】", setmealDTO);
        //调用service新增方法
        setmealService.insert(setmealDTO);
        return Result.success();
    }

    /**
     * 查询回显
     */
    @ApiOperation("查询回显")
    @GetMapping("/{id}")
    public Result<SetmealVO> selectById(@PathVariable Long id) {
        log.info("selectByid() called with parameters => 【id = {}】", id);
        //调用service方法
        SetmealVO setmealVO = setmealService.selectById(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐
     */
    @ApiOperation("修改套餐")
    @PutMapping
    public Result<?> update(@RequestBody SetmealDTO setmealDTO) {
        log.info("update() called with parameters => 【setmealDTO = {}】", setmealDTO);
        //调用修改方法
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 删除套餐
     */
    @ApiOperation("删除套餐")
    @DeleteMapping
    public Result<?> deleteByIds(@RequestParam List<Long> ids){
        log.info("deleteByIds() called with parameters => 【ids = {}】",ids);
        setmealService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 起售停售方法
     */
    @ApiOperation("起售方法")
    @PostMapping("/status/{status}")
    public Result<?> updateStatus(@PathVariable Integer status, Long id){
        log.info("updateStatus() called with parameters => 【status = {}】",status);
        setmealService.updateStatus(status,id);
        return Result.success();
    }
}
