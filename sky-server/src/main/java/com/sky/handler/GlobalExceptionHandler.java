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
 * 统一拦截Controller层异常，返回标准化的Result响应
 *
 * @RestControllerAdvice — @ControllerAdvice + @ResponseBody，全局拦截并以JSON格式返回
 * @ExceptionHandler     — 声明处理的异常类型
 *
 * 处理优先级：DuplicateKeyException → BaseException → Exception（兜底）
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理数据库唯一键冲突异常（如用户名重复）
     *
     * @param e DuplicateKeyException
     * @return 错误响应
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<Void> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("异常信息: {}", e.getMessage());

        String values = Objects.requireNonNull(e.getRootCause()).getMessage();
        if (values.contains("Duplicate entry")) {
            String value = values.split(" ")[2];
            return Result.error(value + MessageConstant.ALREADY_EXIST);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }

    /**
     * 处理业务异常
     *
     * @param e BaseException
     * @return 错误响应
     */
    @ExceptionHandler(BaseException.class)
    public Result<Void> handleBaseException(BaseException e) {
        log.error("异常信息: {}", e.getMessage());
        return Result.error(e.getMessage());
    }

    /**
     * 处理所有未捕获的异常（兜底）
     *
     * @param e Exception
     * @return 错误响应
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("异常信息: ", e);
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
