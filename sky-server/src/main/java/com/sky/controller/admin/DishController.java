package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("新增菜品")
    @PostMapping
    public Result<?> save(@RequestBody DishDTO dishDTO) {
        log.info("save() called with parameters => 【dishDTO = {}】", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
}
