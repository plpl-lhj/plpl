package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;


/**
 * 全局异常处理器
 * @RestControllerAdvice - 组合注解
 *                        @ControllerAdvice + @ResponseBody
 *                        用于全局处理Controller层抛出的异常
 * @ExceptionHandler - 标注在方法上，用于声明处理哪种异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获数据库唯一键冲突异常（如用户名重复）
     * DuplicateKeyException - Spring包装后的异常，需getRootCause()获取原始信息
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("异常信息: {}", e.getMessage());

        // e.getRootCause() - 获取原始异常
        String values = Objects.requireNonNull(e.getRootCause()).getMessage();
        if (values.contains("Duplicate entry")) {
            String value = values.split(" ")[2];
            return Result.error(value + MessageConstant.ALREADY_EXIST);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    /**
     * 捕获业务异常（继承BaseException的所有异常）
     * 返回异常中的友好提示信息
     */
    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e) {
        log.error("异常信息: {}", e.getMessage());

        return Result.error(e.getMessage());
    }

    /**
     * 捕获所有未处理的异常
     * @param e 异常对象
     * @return 返回错误信息
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // e.getMessage() - 获取异常信息
        log.error("异常信息: {}", e);
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
