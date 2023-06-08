package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.InsertAutoFile;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:05
 * @description: TODO
 */

@Mapper
public interface ComboMapper {
    /**
     * 分页查询
     */
    Page<SetmealVO> selectPage(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 执行插入
     * @param setmeal
     */
    @InsertAutoFile
    void insert(Setmeal setmeal);
}
