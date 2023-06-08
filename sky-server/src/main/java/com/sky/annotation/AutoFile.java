package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Inherited
public @interface AutoFile {
    OperationType value();
}
