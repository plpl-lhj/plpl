package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.*;

/**
 * 自动填充注解
 * 标注在Mapper方法上，配合AutoFillAspect切面在方法执行前自动注入审计字段
 *
 * @Target(ElementType.METHOD) — 指定该注解只能标注在方法上
 * @Retention(RetentionPolicy.RUNTIME) — 保留到运行时，反射可以读取到
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    /**
     * 操作类型（INSERT / UPDATE）
     * INSERT → 填充 createTime、createUser、updateTime、updateUser
     * UPDATE → 填充 updateTime、updateUser
     */
    OperationType value();
}
