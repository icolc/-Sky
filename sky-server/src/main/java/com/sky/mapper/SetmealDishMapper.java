package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/7 9:52
 * @description: TODO
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品ID查询对应的套餐id
     * @param dishIds
     * @return
     */
    List<Long> selectSetMealIdsByDishIds(List<Long> dishIds);

    /**
     *
     * @param setmealDishes
     */

    void insert(List<SetmealDish> setmealDishes);
}
