package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

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
}
