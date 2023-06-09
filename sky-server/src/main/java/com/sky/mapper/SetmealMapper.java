package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.InsertAutoFile;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:05
 * @description: TODO
 */

@Mapper
public interface SetmealMapper {
    /**
     * 分页查询
     */
    Page<Setmeal> selectPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 执行插入
     * @param setmeal
     */
    @InsertAutoFile
    void insert(Setmeal setmeal);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Select("select * from setmeal where id = #{id} ;")
    SetmealVO selectById(Integer id);

    /**
     * 修改套餐
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 根据ids查询
     * @param ids
     * @return
     */
    Setmeal selectByIds(List<Integer> ids);

    /**
     * 根据IDS删除
     */
    void deleteByIds(List<Integer> ids);
}
