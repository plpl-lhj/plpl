package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT令牌工具类
 *
 * JWT（JSON Web Token）组成：Header.Payload.Signature
 * - Header：声明算法类型（如HS256）
 * - Payload：携带Claims（用户身份信息）
 * - Signature：签名，防止数据被篡改
 *
 * 本项目使用HMAC-SHA256对称加密算法
 */
@Slf4j
public final class JwtUtil {

    private JwtUtil() {
    }

    /**
     * 生成JWT令牌
     *
     * @param secretKey 签名密钥（HMAC-SHA256算法要求）
     * @param ttlMillis 过期时间（毫秒）
     * @param claims    Payload中的声明数据
     * @return JWT令牌字符串
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 计算过期时间戳
        long expirationMillis = System.currentTimeMillis() + ttlMillis;
        Date expirationDate = new Date(expirationMillis);

        // 构建并签名JWT令牌
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .expiration(expirationDate)
                .claims(claims)
                .compact();
    }

    /**
     * 解析并验证JWT令牌
     *
     * @param secretKey 签名密钥（必须与生成时一致）
     * @param token     JWT令牌字符串
     * @return Claims声明对象（包含用户身份信息）
     * @throws io.jsonwebtoken.JwtException 令牌无效或过期时抛出
     */
    public static Claims parseJWT(String secretKey, String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
