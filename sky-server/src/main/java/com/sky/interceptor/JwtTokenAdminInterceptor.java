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
 * @Component — 将该类标记为Spring组件，会被扫描并注册到Spring容器中
 * HandlerInterceptor — Spring MVC的拦截器接口
 *                      实现该接口可以拦截请求，在请求处理前进行预处理
 * 作用：拦截所有/admin/**请求（登录接口除外），校验JWT令牌是否有效
 *       校验通过后将用户id存入ThreadLocal，跨层传递
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
     * preHandle — 前置处理方法，在请求处理之前调用
     *             返回true表示放行，false表示拦截
     *
     * 执行流程：
     * 1. 判断是否映射到Controller方法（静态资源直接放行）
     * 2. 从请求头中获取token
     * 3. 解析并验证JWT令牌
     * 4. 解析成功 → 将用户id存入ThreadLocal，放行
     * 5. 解析失败 → 响应401状态码，拦截
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器(Controller中的方法)
     * @return true-放行，false-拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.判断当前请求是否是映射到方法上(而非静态资源)
        // HandlerMethod — Spring MVC封装，表示Controller中一个具体的方法
        if (!(handler instanceof HandlerMethod)) {
            // 当前请求不是映射到方法上，直接放行（例如请求静态资源）
            return true;
        }

        // 2.从请求头中获取令牌
        // request.getHeader() — 从请求头获取指定名称的数据
        // jwtProperties.getAdminTokenName() — 获取令牌名称，默认是"token"
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 3.校验令牌
        try {
            log.info("jwt令牌校验开始...");
            // JwtUtil.parseJWT() — 解析并验证JWT令牌
            // 验证失败会抛出异常，进入catch块
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);

            // claims.get() — 从JWT payload中获取自定义数据
            Long employeeId = Long.valueOf(claims.get(JwtClaimsConstant.EMPLOYEE_ID).toString());
            log.info("当前员工id: {}", employeeId);

            // 4.将员工id存入ThreadLocal，供后续Service层使用
            // ThreadLocal — 线程局部变量，同一请求线程内跨层传递数据
            Map<String, Object> map = new HashMap<>();
            map.put(BaseConstant.ID, employeeId);
            BaseContext.setThreadLocal(map);

            // 5.放行
            return true;
        } catch (Exception e) {
            // 6.令牌校验失败，响应401状态码
            log.error("jwt令牌校验失败: {}", e.getMessage());
            // response.setStatus() — 设置HTTP响应状态码
            // 401 — 未授权/认证失败
            response.setStatus(401);
            return false;
        }
    }

    /**
     * 请求完成后的清理工作
     * afterCompletion — 在整个请求处理完成后调用（包括视图渲染后）
     *                   无论preHandle返回true还是false都会执行
     *                   用于释放资源，如清理ThreadLocal防止内存泄漏
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束，从ThreadLocal中移除用户信息，防止内存泄漏
        BaseContext.removeThreadLocal();
    }
}
