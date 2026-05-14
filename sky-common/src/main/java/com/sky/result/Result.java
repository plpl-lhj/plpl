package com.sky.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 * @param <T> 返回数据的类型
 * Serializable - 实现序列化接口，用于对象传输和存储
 */
@Data
public class Result<T> implements Serializable {
    // 编码：1成功，0及其它数字为失败
    private Integer code;
    // 错误信息
    private String msg;
    // 数据
    private T data;

    /**
     * 返回成功结果(无数据)
     * @param <T> 类型参数
     * @return Result对象
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    /**
     * 返回成功结果(带数据)
     * @param data 返回的数据
     * @param <T> 类型参数
     * @return Result对象
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.code = 1;
        result.data = data;
        return result;
    }

    /**
     * 返回失败结果
     * @param msg 错误信息
     * @param <T> 类型参数
     * @return Result对象
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
