package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * @param
     * @return
     */
    List<Long> selectSetMealIdsByDishIds(List<Long> ids);

    /**
     *插入
     * @param setmealDishes
     */
    void insert(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> selectBySetmealId(Long id);

    /**
     * 删除套餐_菜品中间表信息
     * @param ids
     */
    void deleteBatchBySetmealId(List<Long> ids);


    List<Long> selectDishIdBySetmealId(Long id);
}
