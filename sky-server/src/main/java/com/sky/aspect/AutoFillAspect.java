package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.constant.BaseConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自动填充切面类
 * 拦截Mapper层带有@AutoFill注解的方法，在执行前自动注入审计字段
 *
 * @Aspect    — AOP注解，声明该类为切面（切入点Pointcut + 通知Advice）
 * @Component — 注册为Spring组件
 * @Slf4j     — Lombok日志注解
 */
@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点定义
     * execution(* com.sky.mapper.*.*(..)) — 匹配mapper包下所有类的所有方法
     * @annotation(com.sky.annotation.AutoFill) — 仅匹配标注了@AutoFill注解的方法
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
        // 切入点声明方法体，仅用于定义切入点表达式
    }

    /**
     * 前置通知：在目标方法执行前自动填充审计字段
     *
     * @Before — AOP前置通知注解，在目标方法执行前执行
     *
     * 执行流程：
     * 1. 从连接点获取方法参数（第一个参数为实体对象）
     * 2. 解析@AutoFill注解，获取操作类型（INSERT/UPDATE）
     * 3. 通过反射调用实体的setter方法注入当前时间和操作人ID
     *
     * @param joinPoint 连接点（包含目标方法信息和参数）
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充审计字段...");

        // 1. 获取方法参数（第一个参数为实体对象）
        Object[] methodArgs = joinPoint.getArgs();
        if (methodArgs == null || methodArgs.length == 0) {
            return;
        }
        Object entity = methodArgs[0];

        // 2. 解析@AutoFill注解，获取操作类型
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFillAnnotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFillAnnotation.value();

        // 3. 准备注入数据
        LocalDateTime now = LocalDateTime.now();
        Long currentUserId = Long.valueOf(
                BaseContext.getThreadLocal().get(BaseConstant.ID).toString()
        );

        // 4. 根据操作类型通过反射填充审计字段
        if (operationType == OperationType.INSERT) {
            fillInsertAuditFields(entity, now, currentUserId);
        } else if (operationType == OperationType.UPDATE) {
            fillUpdateAuditFields(entity, now, currentUserId);
        }
    }

    /**
     * 填充INSERT操作的审计字段（createTime、createUser、updateTime、updateUser）
     */
    private void fillInsertAuditFields(Object entity, LocalDateTime now, Long userId) {
        try {
            Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setCreateTime.invoke(entity, now);
            setUpdateTime.invoke(entity, now);
            setCreateUser.invoke(entity, userId);
            setUpdateUser.invoke(entity, userId);
        } catch (Exception e) {
            log.error("自动填充INSERT审计字段失败: {}", e.getMessage());
        }
    }

    /**
     * 填充UPDATE操作的审计字段（updateTime、updateUser）
     */
    private void fillUpdateAuditFields(Object entity, LocalDateTime now, Long userId) {
        try {
            Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

            setUpdateTime.invoke(entity, now);
            setUpdateUser.invoke(entity, userId);
        } catch (Exception e) {
            log.error("自动填充UPDATE审计字段失败: {}", e.getMessage());
        }
    }
}
