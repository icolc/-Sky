package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
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
    SetmealVO selectById(Integer id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 根据ids删除
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 起售
     * @param status
     */
    void updateStatus(Integer status,Integer id);
}
