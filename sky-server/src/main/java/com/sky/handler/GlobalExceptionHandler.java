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
 * @RestControllerAdvice — 组合注解
 *                         @ControllerAdvice + @ResponseBody
 *                         ControllerAdvice用于全局拦截Controller异常
 *                         ResponseBody使返回值直接写入响应体（JSON格式）
 * @ExceptionHandler — 标注在方法上，声明当前方法处理哪种异常类型
 *
 * 处理顺序：Spring会匹配最具体的异常类型
 * DuplicateKeyException → BaseException及其子类 → Exception（兜底）
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获数据库唯一键冲突异常（如用户名重复）
     * DuplicateKeyException — MyBatis/Spring对MySQL唯一约束冲突的包装异常
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("异常信息: {}", e.getMessage());

        // e.getRootCause() — 获取原始的MySQL异常信息（如"Duplicate entry 'xxx' for key"）
        String values = Objects.requireNonNull(e.getRootCause()).getMessage();
        if (values.contains("Duplicate entry")) {
            // 按空格分割，第3个元素是重复的值
            String value = values.split(" ")[2];
            return Result.error(value + MessageConstant.ALREADY_EXIST);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

    /**
     * 捕获业务异常（继承BaseException的所有自定义异常）
     */
    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e) {
        log.error("异常信息: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 捕获所有未处理的异常（兜底处理器）
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("异常信息: ", e);
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
