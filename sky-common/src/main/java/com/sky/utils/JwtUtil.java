package com.sky.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Jwt令牌工具类
 * JWT组成：Header.Payload.Signature
 * - Header：包含算法和类型
 * - Payload：包含claims（用户信息）
 * - Signature：签名，防止数据被篡改
 */
@Slf4j
public final class JwtUtil {
    // 私有构造函数，防止实例化
    private JwtUtil() {
    }

    /**
     * jwt令牌加密(生成token)
     * @param secretKey 密钥，用于签名
     * @param ttlMillis 过期时间(毫秒)
     * @param claims 令牌中携带的用户信息
     * @return 加密后的token字符串
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 1. 指定签名的时候使用的签名算法
        // HMAC-SHA256是对称加密算法，需要同一密钥进行签名和验证
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 2. 生成jwt令牌
        // Jwts.builder() - JWT构建器
        // signWith() - 设置签名算法和密钥
        // Keys.hmacShaKeyFor() - 将字符串密钥转换为Key对象
        // expiration() - 设置过期时间
        // claims() - 设置payload中的数据
        // compact() - 生成token字符串
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .expiration(exp)
                .claims(claims)
                .compact();
    }

    /**
     * jwt令牌解密(解析token)
     * @param secretKey 密钥，用于验证签名
     * @param token 令牌字符串
     * @return 令牌中携带的用户信息(claims)
     */
    public static Claims parseJWT(String secretKey, String token) {
        // Jwts.parser() - 获取JWT解析器
        // verifyWith() - 设置验证签名的密钥
        // build() - 构建解析器
        // parseSignedClaims() - 解析token并验证签名
        // getPayload() - 获取payload中的claims
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
