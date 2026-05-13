package com.sky.context;

import java.util.Map;

/**
 * ThreadLocal上下文
 * ThreadLocal - 线程局部变量，每个线程各有一份独立的副本
 * 作用：在同一个请求线程中跨层传递数据（如拦截器存id -> Service取id）
 * 特点：线程间互不干扰，用完必须remove()，否则内存泄漏
 */
public class BaseContext {
    // 存储当前线程的上下文数据
    private static final ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    // 设置上下文数据
    public static void setThreadLocal(Map<String, Object> map) {
        threadLocal.set(map);
    }

    // 获取上下文数据
    public static Map<String, Object> getThreadLocal() {
        return threadLocal.get();
    }

    // 清除上下文数据（请求结束后必须调用，防止内存泄漏）
    public static void removeThreadLocal() {
        threadLocal.remove();
    }
}
