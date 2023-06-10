package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/10 15:05
 * @description:
 */
@Api(tags = "店铺相关接口")
@RestController("adminShopController")
@RequestMapping("admin/shop")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String KEY="SHOP_STATUS";
    /**
     * 设置营业状态
     * @param status
     * @return
     */
    @ApiOperation("/设置店铺营业状态")
    @PutMapping("/{status}")
    public Result<?> setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为:{}",status == 1 ? "营业中":"打烊中");
        redisTemplate.opsForValue().set(KEY,status);
        return Result.success();
    }

    /**
     * 查询店铺状态
     */
    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get(KEY);
        log.info("设置店铺的营业状态为:{}", shopStatus == 1 ? "营业中":"打烊中");
        return Result.success(shopStatus);
    }
}
