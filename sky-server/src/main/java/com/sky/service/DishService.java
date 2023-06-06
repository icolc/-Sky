package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 20:14
 * @description:
 */
public interface DishService {
    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}
