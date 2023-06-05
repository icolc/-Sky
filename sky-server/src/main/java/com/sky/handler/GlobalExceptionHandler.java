package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.exception.PhoneIsErrorException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 手机号格式错误
     * @param e
     * @return
     */
    @ExceptionHandler(PhoneIsErrorException.class)
    public Result<?> phoneIsErrorException(PhoneIsErrorException e) {
        log.error("异常信息：{}", e.getMessage(), e);
        return Result.error(e.getMessage());
    }

    /**
     * SQLIntegrityConstraintViolationException     异常捕获
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<?> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getMessage();
        log.error(message, e);
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            String m = s[2] + MessageConstant.ALREADY_EXISTS;
            return Result.error(m);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
