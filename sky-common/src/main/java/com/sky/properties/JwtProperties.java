package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性类
 * @Component - 将该类标记为组件，纳入Spring管理
 * @ConfigurationProperties - 绑定配置文件中的属性
 *                           prefix指定前缀，Spring Boot会自动映射
 *                           例如：sky.jwt.admin-secret-key -> adminSecretKey
 */
@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data

public class JwtProperties {
    // 管理端员工生成jwt令牌相关配置
    // jwt签名加密时使用的秘钥
    private String adminSecretKey;
    // jwt过期时间(毫秒)
    private long adminTtl;
    // 前端传递过来的令牌名称
    private String adminTokenName;
}
