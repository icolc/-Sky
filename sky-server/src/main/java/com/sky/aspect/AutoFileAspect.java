package com.sky.aspect;

import com.sky.annotation.AutoFile;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

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
    @Pointcut("@annotation(com.sky.annotation.AutoFile) && execution(* com.sky.mapper.*.*(..))")
    public void autoFilePointCut() {
    }

    @Before("autoFilePointCut()")
    public void autoFile(JoinPoint joinPoint) throws Exception {
        log.info("自动填充切面运行了");
        //获取方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法上的注解对象
        AutoFile autoFile = signature.getMethod().getAnnotation(AutoFile.class);
        //获取注解对象上的操作类型
        OperationType value = autoFile.value();
        //再获取当前拦截的方法参数
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args)) {
            return;
        }
        Object arg = args[0];
        //准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //根据不同的操作类型，为对应的方法赋值
        if (value == OperationType.INSERT) {
            //为所有的赋值
            Method setCreateTime = arg.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
            Method setCreateUser = arg.getClass().getDeclaredMethod("setCreateUser", Long.class);
            Method setUpdateTime = arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = arg.getClass().getDeclaredMethod("setUpdateUser", Long.class);

            setCreateTime.invoke(arg, now);
            setCreateUser.invoke(arg, currentId);
            setUpdateTime.invoke(arg, now);
            setUpdateUser.invoke(arg, currentId);
        } else if (Objects.equals(arg, OperationType.UPDATE)) {
            //为2个字段赋值
            Method setUpdateTime = arg.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = arg.getClass().getDeclaredMethod("setUpdateUser", Long.class);
            setUpdateTime.invoke(arg, now);
            setUpdateUser.invoke(arg, currentId);
        }
    }
}
