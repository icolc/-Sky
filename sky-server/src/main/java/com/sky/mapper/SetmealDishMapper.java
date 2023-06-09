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
     * @param dishIds
     * @return
     */
    List<Long> selectSetMealIdsByDishIds(List<Long> dishIds);

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

    /**
     * 根据setmealID查询所有的菜品id
     * @param id
     * @return
     */
    @Select("select count(*) from setmeal_dish where setmeal_id = #{id}")
    List<Integer> selectAllBySetmealId(Long id);
}
