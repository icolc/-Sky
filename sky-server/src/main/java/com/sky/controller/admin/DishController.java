package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 20:13
 * @description:
 */
@Slf4j
@Api(tags = "菜品接口")
@RequestMapping("/admin/dish")
@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result<?> save(@RequestBody DishDTO dishDTO) {
        log.info("save() called with parameters => 【dishDTO = {}】", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("page() called with parameters => 【dishPageQueryDTO = {}】", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     */
    @ApiOperation("删除菜品")
    @DeleteMapping
    public Result<?> deleteByIds(@RequestParam List<Long> ids) {
        log.info("deleteByIds() called with parameters => 【ids = {}】", ids);
        //开始删除
        dishService.deleteByIds(ids);
        return Result.success();
    }
}
