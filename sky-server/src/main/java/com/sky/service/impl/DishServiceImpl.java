package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 20:15
 * @description:
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        //先创建一个dish对象，拷贝属性
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        //向菜品表插入数据
        dishMapper.insert(dish);
        //取出每个菜品的id
        Long id = dish.getId();
        //判断该商品的口味是否没有
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (Objects.nonNull(flavors) && flavors.size() > 0) {
            //将菜品id传进口味里
            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            //有口味：向口味表中插入数据
            dishFlavorMapper.insertBath(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //设置分页参数
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID批量删除
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> ids) {
        //判断是否为null
        if (Objects.isNull(ids)) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_IDS_IS_NULL);
        }
        //判断是否是起售中的菜品
        for (Long id : ids) {
            //根据ID查询
            Dish dish = dishMapper.selectById(id);
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
                //当前菜品处于起售状态
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断是否关联了套餐
        List<Long> setMealIds = setmealDishMapper.selectSetMealIdsByDishIds(ids);
        if (Objects.nonNull(setMealIds) && setMealIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除菜品表的数据
        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }

    }

    /**
     * 根据id查询菜品以及口味数据
     * @param id
     * @return
     */
    @Override
    public DishVO selectByIdWithFlavor(Long id) {
        //先查询菜品表
        Dish dish = dishMapper.selectById(id);
        //再查口味数据
        List<DishFlavor> dishFlavors = dishFlavorMapper.selectByDishId(id);
        //封装到一个VO对象
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        //先拷贝到Dish对象
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        //修改菜品基本信息
        dishMapper.update(dish);
        //删除口味数据
        dishFlavorMapper.deleteByDishId(dishDTO.getId());
        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (Objects.nonNull(flavors) && flavors.size() > 0) {
            //将菜品id传进口味里
            flavors.forEach(flavor -> {
                flavor.setDishId(dishDTO.getId());
            });
            //有口味：向口味表中插入数据
            dishFlavorMapper.insertBath(flavors);
        }
    }

    /**
     * 根据分类查询
     * @param categoryId
     * @return
     */
    @Override
    public List<DishVO> selectList(Integer categoryId) {
        log.info("selectList() called with parameters => 【categoryId = {}】",categoryId);
        return dishMapper.selectByCategoryId(categoryId);
    }

    /**
     * 菜品停售或起售
     * @param status
     * @param id
     */
    @Override
    public void statusOrStop(Integer status, Long id) {
        dishMapper.statusOrStop(status,id);
    }
}
