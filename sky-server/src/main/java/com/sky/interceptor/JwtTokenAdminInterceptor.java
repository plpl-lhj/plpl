package com.sky.interceptor;

import com.sky.constant.BaseConstant;
import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT令牌校验拦截器
 * 拦截/admin/**请求（登录接口除外），校验JWT令牌有效性
 * 校验通过后将用户ID存入ThreadLocal，供后续Service层使用
 *
 * @Component          — 注册为Spring组件
 * @Slf4j              — Lombok日志注解
 * HandlerInterceptor  — Spring MVC拦截器接口，实现preHandle/afterCompletion方法
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    /**
     * 构造函数注入JWT配置
     *
     * @param jwtProperties JWT配置属性
     */
    public JwtTokenAdminInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 前置处理：校验JWT令牌
     * 返回true放行，返回false拦截
     *
     * @param request   HTTP请求对象
     * @param response  HTTP响应对象
     * @param handler   处理器（Controller方法或静态资源）
     * @return true-放行，false-拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. 判断是否映射到Controller方法（静态资源直接放行）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 2. 从请求头获取JWT令牌
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 3. 解析并验证令牌
        try {
            log.info("JWT令牌校验开始...");
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);

            // 4. 解析成功，提取用户ID存入ThreadLocal
            Long employeeId = Long.valueOf(claims.get(JwtClaimsConstant.EMPLOYEE_ID).toString());
            log.info("当前员工ID: {}", employeeId);

            Map<String, Object> contextData = new HashMap<>();
            contextData.put(BaseConstant.ID, employeeId);
            BaseContext.setThreadLocal(contextData);

            return true;
        } catch (Exception e) {
            // 5. 令牌校验失败，返回401未授权
            log.error("JWT令牌校验失败: {}", e.getMessage());
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 请求完成后清理ThreadLocal，防止内存泄漏
     * 无论preHandle返回true还是false都会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        BaseContext.removeThreadLocal();
    }
}
