package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 20:37
 * @description:
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBath(List<DishFlavor> flavors);

    /**
     * 根据菜品ID删除口味数据
     * @param dishId
     */
    @Delete("delete from dish_flavor where id=#{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id查询口味
     * @param id
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{id} ;")
    List<DishFlavor> selectByDishId(Long id);
}
