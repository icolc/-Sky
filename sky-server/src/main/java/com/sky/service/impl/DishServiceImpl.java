package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 20:15
 * @description:
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //先创建一个dish对象，拷贝属性
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入数据
        dishMapper.insert(dish);
        //取出每个菜品的id
        Long id = dish.getId();
        //判断该商品的口味是否没有
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (Objects.nonNull(flavors) && flavors.size() > 0){
            //将菜品id传进口味里
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            //有口味：向口味表中插入数据
            dishFlavorMapper.insertBath(flavors);
        }
    }
}
