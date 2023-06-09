package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/8 15:04
 * @description: 套餐
 */
@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    public DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 套餐分页查询
     *
     * @return
     */
    @Override
    public PageResult selectPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        //设置分页参数
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        //调用分页查询
        Page<Setmeal> setmeals = setmealMapper.selectPage(setmealPageQueryDTO);
        //创建vo对象
        SetmealVO setmealVO = new SetmealVO();
        //加工数据
        List<SetmealVO> setmealVoList = setmeals.getResult().stream().map(setmeal -> {
            //进行数据拷贝
            BeanUtils.copyProperties(setmeal, setmealVO);
            //根据id查询分类
            Category category = categoryMapper.getCategoryNameById(setmeal.getCategoryId());
            //添加
            setmealVO.setCategoryName(category.getName());
            return setmealVO;
        }).collect(Collectors.toList());
        return new PageResult(setmeals.getTotal(), setmealVoList);
    }

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        //执行插入
        setmealMapper.insert(setmeal);
        //维护套餐菜品关系
        setmealDTO.getSetmealDishes().stream().forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insert(setmealDTO.getSetmealDishes());
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO selectById(Integer id) {
        return setmealMapper.selectById(id);
    }

    /**
     * 修改套餐
     *
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        log.info("update() called with parameters => 【setmealDTO = {}】", setmealDTO);
        //进行属性拷贝
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //执行修改操作
        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteByIds(List<Integer> ids) {
        //判断是否为null
        if (Objects.isNull(ids)) {
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_IDS_IS_NULL);
        }
        //查询，看看是否在起售
        Setmeal setmeal = setmealMapper.selectByIds(ids);
        if (Objects.nonNull(setmeal)) {
            //有套餐在起售
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        }
        //删除套餐
        setmealMapper.deleteByIds(ids);
    }

    /**
     * 起售
     *
     * @param status
     */
    @Override
    public void updateStatus(Integer status, Integer id) {
        //判断该id下的商品是否有未起售的
        SetmealVO setmealVO = setmealMapper.selectById(id);
        //判断数值
        if (Objects.equals(status, StatusConstant.ENABLE)) {

        }
    }

}
