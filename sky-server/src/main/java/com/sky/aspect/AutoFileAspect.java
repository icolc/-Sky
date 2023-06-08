package com.sky.aspect;

import com.sky.annotation.AutoFile;
import com.sky.annotation.InsertAutoFile;
import com.sky.annotation.UpdateAutoFile;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/6/6 15:23
 * @description: 自定义切面类
 */
@Aspect
@Component
@Slf4j
public class AutoFileAspect {
    /**
     * 切入点表达式
     */
    @Pointcut("@annotation(com.sky.annotation.AutoFile) " +
            "|| @annotation(com.sky.annotation.InsertAutoFile) " +
            "|| @annotation(com.sky.annotation.UpdateAutoFile)")
    public void autoFilePointCut() {
    }

    @Before("autoFilePointCut()")
    public void autoFile(JoinPoint joinPoint) throws Exception {
        log.info("自动填充切面运行了");
        //获取当前执行方法的参数列表
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args) && args.length > 0) {
            return;
        }
        //取出参数列表
        Object entity = args[0];
        //获得这个方法对象
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        try {
            //获取方法上的注解对象
            AutoFile autoFile = method.getAnnotation(AutoFile.class);
            //准备赋值的数据
            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();
            //根据不同的操作类型，为对应的方法赋值
            if (Objects.nonNull(autoFile)) {
                //获取注解中的属性
                OperationType value = autoFile.value();
                //判断属性
                if (Objects.equals(OperationType.INSERT, value)) {
                    //插入
                    autoInsert(entity, now, currentId);
                } else {
                    //修改
                    autoUpdate(entity, now, currentId);
                }
            }
            //如果是子注解，直接判断
            if (method.isAnnotationPresent(InsertAutoFile.class)) {
                autoInsert(entity, now, currentId);
            }
            if (method.isAnnotationPresent(UpdateAutoFile.class)) {
                autoUpdate(entity, now, currentId);
            }
        } catch (Exception e) {
            log.error("自动赋值失败");
        }
    }

    /**
     * 自动填充修改
     * @param entity
     * @param now
     * @param currentId
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void autoUpdate(Object entity, LocalDateTime now, Long currentId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
        Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        setUpdateTime.invoke(entity, now);
        setUpdateUser.invoke(entity, currentId);
    }

    /**
     * 自动填充新增
     * @param entity
     * @param now
     * @param currentId
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void autoInsert(Object entity, LocalDateTime now, Long currentId) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
        Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
        Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        //赋值
        setCreateTime.invoke(entity, now);
        setCreateUser.invoke(entity, currentId);
        setUpdateTime.invoke(entity, now);
        setUpdateUser.invoke(entity, currentId);
    }
}
