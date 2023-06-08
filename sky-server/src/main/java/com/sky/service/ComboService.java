package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:04
 * @description: TODO
 */
public interface ComboService {

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
}
