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
 * Jwt令牌校验拦截器
 * @Component - 将该类标记为Spring组件，会被扫描并注册到Spring容器中
 * HandlerInterceptor - Spring MVC的拦截器接口
 *                      实现该接口可以拦截请求，在请求处理前进行预处理
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {
    private final JwtProperties jwtProperties;

    public JwtTokenAdminInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 校验jwt令牌
     * preHandle - 前置处理方法，在请求处理之前调用
     *            返回true表示放行，false表示拦截
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(Controller中的方法)
     * @return true-放行，false-拦截
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前请求是否是映射到方法上(而不是静态资源)
        // HandlerMethod - 表示Controller中的方法
        if (!(handler instanceof HandlerMethod)) {
            // 当前请求不是映射到方法上，直接放行(例如请求静态资源)
            return true;
        }

        // 1. 从请求头中获取令牌
        // request.getHeader() - 从请求头获取数据
        // jwtProperties.getAdminTokenName() - 获取令牌名称，默认是"token"
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 2. 校验令牌
        try {
            log.info("jwt令牌校验开始...");
            // JwtUtil.parseJWT() - 解析并验证JWT令牌
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            // claims.get() - 从JWT payload中获取数据
            Long employeeId = Long.valueOf(claims.get(JwtClaimsConstant.EMPLOYEE_ID).toString());
            log.info("当前员工id: {}", employeeId);

            // 将员工id存入threadLocal中
            Map<String, Object> map = new HashMap<>();
            map.put(BaseConstant.ID, employeeId);
            BaseContext.setThreadLocal(map);

            // 3. 放行
            return true;
        } catch (Exception e) {
            // 4. 不通过，响应401状态码
            log.error("jwt令牌校验失败: {}", e.getMessage());
            // response.setStatus() - 设置HTTP响应状态码
            // 401表示未授权/认证失败
            response.setStatus(401);
            return false;
        }
    }
}
