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
 * 自动填充切面
 * @Aspect — 声明该类是一个切面，Spring AOP会自动扫描并处理
 *           切面 = 切入点(Pointcut) + 通知(Advice)
 *
 * 作用：在Mapper执行insert/update之前，自动注入当前时间和操作人id
 *       避免在每个Service实现类中重复编写审计字段赋值代码
 */
@Component
@Aspect
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点定义
     * 匹配规则由 execution 表达式 + @annotation 组成：
     * {@code execution(* com.sky.mapper.*.*(..))}
     *   - 第一个 * 表示任意返回类型
     *   - com.sky.mapper.* 表示该包下的所有类
     *   - 第二个 * 表示所有方法名
     *   - (..) 表示任意参数列表
     * {@code && @annotation(com.sky.annotation.AutoFill)}
     *   - 限制只匹配标注了 @AutoFill 的方法
     *
     * 整体含义：拦截 com.sky.mapper 包中所有带有 @AutoFill 注解的方法
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
        // 切入点声明方法，不需要方法体
    }

    /**
     * 前置通知：在目标方法执行前自动填充审计字段
     * @Before — 前置通知，在目标方法执行之前执行
     * autoFillPointCut() — 引用上面定义的切入点
     *
     * 执行流程：
     * 1. 从连接点获取 @AutoFill 注解，判断是 INSERT 还是 UPDATE
     * 2. 获取方法的第一个参数（即要持久化的实体对象）
     * 3. 通过反射调用对应的 setter 方法注入当前时间和操作人id
     *
     * @param joinPoint 连接点，可获取目标方法的信息和参数
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("自动填充审计字段...");

        // 1.获取方法参数（第一个参数预期是实体对象）
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        // 2.获取方法签名并解析 @AutoFill 注解
        // MethodSignature — 封装了目标方法的信息（方法名、参数、返回值、注解等）
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // getMethod() — 获取被拦截的Method对象
        // getAnnotation(AutoFill.class) — 获取该方法上的@AutoFill注解
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        // autoFill.value() — 获取注解中设置的枚举值（INSERT / UPDATE）
        OperationType operationType = autoFill.value();

        // 3.准备要注入的数据
        LocalDateTime now = LocalDateTime.now();
        Long userId = Long.valueOf(BaseContext.getThreadLocal().get(BaseConstant.ID).toString());

        // 4.根据操作类型填充相应的审计字段
        if (operationType == OperationType.INSERT) {
            // INSERT 需要填充全部4个审计字段
            try {
                // getDeclaredMethod(方法名, 参数类型) — 通过反射获取指定名称和参数类型的Method对象
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // invoke(目标对象, 参数值) — 调用获取到的Method方法，相当于 entity.setCreateTime(now)
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, userId);
                setUpdateUser.invoke(entity, userId);
            } catch (Exception e) {
                log.error("自动填充INSERT审计字段失败: {}", e.getMessage());
            }
        } else if (operationType == OperationType.UPDATE) {
            // UPDATE 只需要填充更新时间和更新人
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
}
