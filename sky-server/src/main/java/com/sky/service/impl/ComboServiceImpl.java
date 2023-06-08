package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.mapper.ComboMapper;
import com.sky.result.PageResult;
import com.sky.service.ComboService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:04
 * @description:
 */
@Slf4j
@Service
public class ComboServiceImpl implements ComboService {
    @Autowired
    private ComboMapper comboMapper;

    /**
     * 套餐分页查询
     * @return
     */
    @Override
    public PageResult selectPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        //设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //调用分页查询
        Page<SetmealVO> setmealVOS = comboMapper.selectPage(setmealPageQueryDTO);
        return new PageResult(setmealVOS.getTotal(), setmealVOS.getResult());
    }

    /**
     * 新增套餐
     */
    @Override
    public void insert(SetmealDTO setmealDTO) {
        //拷贝属性
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //执行插入
        comboMapper.insert(setmeal);
    }
}
