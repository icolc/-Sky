package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:04
 * @description: TODO
 */
public interface SetmealService {

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult selectPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 新增方法
     * @param setmealDTO
     */
    void insert(SetmealDTO setmealDTO);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    SetmealVO selectById(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 根据ids删除
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 起售
     * @param status
     */
    void updateStatus(Integer status,Long id);

    /**
     * 根据分类id查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询包含的菜品列表
     * @param id
     * @return
     */
    List<DishItemVO> selectDishItemById(Long id);
}
