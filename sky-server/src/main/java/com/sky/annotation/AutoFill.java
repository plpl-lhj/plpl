package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.*;

/**
 * 自动填充注解
 * 标注在Mapper方法上，配合AutoFillAspect切面在执行前自动注入审计字段
 *
 * @Target(ElementType.METHOD)    — 元注解，限定该注解只能标注在方法上
 * @Retention(RetentionPolicy.RUNTIME) — 元注解，保留到运行时（反射可读取）
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    /**
     * 操作类型
     * INSERT — 填充createTime、createUser、updateTime、updateUser
     * UPDATE — 填充updateTime、updateUser
     *
     * @return 操作类型枚举
     */
    OperationType value();
}
