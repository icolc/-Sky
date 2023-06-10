package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.InsertAutoFile;
import com.sky.annotation.UpdateAutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     *
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增菜品
     *
     * @param dish 使用自动注入公共字段注解
     */
    @InsertAutoFile
    void insert(Dish dish);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user from dish where id = #{id}")
    Dish selectById(Long id);

    /**
     * 根据ID删除
     *
     * @param id
     */
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * 修改菜品数据
     *
     * @param dish
     */
    @UpdateAutoFile
    void update(Dish dish);

    /**
     * 根据分类id查询
     *
     * @param categoryId
     * @return
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<DishVO> selectByCategoryId(Integer categoryId);


    @Update("update dish set status = #{status} where id = #{id}")
    void statusOrStop(Integer status, Long id);

    /**
     * 根据名字查询
     * @param name
     * @return
     */
    List<DishVO> selectByName(String name);

    /**
     * 查询状态
     * @param dishIds
     * @return
     */
    Integer selectBatchDishStatusByIds(List<Long> dishIds);
}
