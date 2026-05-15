package com.sky.result;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应结果封装类
 * 规范化API返回格式：code=1表示成功，code=0表示失败
 *
 * @param <T> 响应数据类型
 */
@Data
public class Result<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 状态码（1:成功 0:失败） */
    private Integer code;

    /** 错误信息（成功时为null） */
    private String msg;

    /** 响应数据（失败时为null） */
    private T data;

    /**
     * 返回成功结果（无数据）
     *
     * @param <T> 类型参数
     * @return Result实例
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }

    /**
     * 返回成功结果（带数据）
     *
     * @param data 响应数据
     * @param <T>  类型参数
     * @return Result实例
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.data = data;
        return result;
    }

    /**
     * 返回失败结果
     *
     * @param msg 错误信息
     * @param <T> 类型参数
     * @return Result实例
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
