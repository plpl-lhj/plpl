package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性类
 * 绑定application.yml中sky.jwt开头的配置项
 *
 * @Component             — 注册为Spring组件
 * @ConfigurationProperties — 将配置文件中的属性映射到Java对象
 */
@Component
@ConfigurationProperties(prefix = "sky.jwt")
@Data
public class JwtProperties {

    /**
     * JWT签名密钥（用于HMAC-SHA256算法）
     * 要求：足够长的随机字符串，建议32字符以上
     */
    private String adminSecretKey;

    /**
     * JWT过期时间（毫秒）
     * 示例：7200000 = 2小时
     */
    private long adminTtl;

    /**
     * 请求头中的令牌名称
     * 前端需在请求头中添加此名称的Header携带Token
     */
    private String adminTokenName;
}
