package com.sky.context;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal上下文持有者
 *
 * ThreadLocal — 线程本地变量，为每个线程提供独立的变量副本
 * 核心作用：在同一个请求线程内跨层传递数据（如拦截器→Service→Mapper）
 * 生命周期：请求开始时set，请求结束时必须remove，否则导致内存泄漏
 * 典型场景：JWT拦截器解析出userId后存入ThreadLocal，Service层取出使用
 */
public class BaseContext {

    /**
     * ThreadLocal实例，存储当前线程的上下文数据
     * 使用Map结构便于扩展（可存储多个key-value）
     */
    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置上下文数据
     *
     * @param contextData 上下文数据Map
     */
    public static void setThreadLocal(Map<String, Object> contextData) {
        THREAD_LOCAL.set(contextData);
    }

    /**
     * 获取上下文数据
     *
     * @return 上下文数据Map
     */
    public static Map<String, Object> getThreadLocal() {
        return THREAD_LOCAL.get();
    }

    /**
     * 清除上下文数据
     * 必须在请求结束时调用（通常在拦截器afterCompletion中），防止内存泄漏
     */
    public static void removeThreadLocal() {
        THREAD_LOCAL.remove();
    }
}
