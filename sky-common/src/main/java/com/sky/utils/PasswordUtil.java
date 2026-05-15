package com.sky.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.slf4j.Slf4j;

/**
 * 密码工具类
 * 使用Argon2id算法进行密码哈希和验证
 *
 * Argon2 — 2015年Password Hashing Competition（PHC）冠军算法
 * 特点：抗GPU暴力破解、抗ASIC专用芯片攻击、抗侧信道攻击
 * 变体：Argon2id（混合模式，兼顾安全性和性能）
 */
@Slf4j
public final class PasswordUtil {

    private PasswordUtil() {
    }

    /** Argon2id实例（混合模式，兼顾GPU抗性和侧信道安全） */
    private static final Argon2 ARGON2_INSTANCE = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    /** 迭代次数（时间成本），值越大计算越慢，暴力破解成本越高 */
    private static final int ITERATIONS = 3;

    /** 内存成本（KB），64MB，增加GPU攻击的内存消耗 */
    private static final int MEMORY_COST = 64 * 1024;

    /** 并行度（线程数），利用多核CPU优势 */
    private static final int PARALLELISM = 4;

    /**
     * 对密码进行哈希
     *
     * @param rawPassword 明文密码
     * @return 哈希后的密码字符串（含算法参数、盐值、哈希值）
     */
    public static String hash(String rawPassword) {
        return ARGON2_INSTANCE.hash(ITERATIONS, MEMORY_COST, PARALLELISM, rawPassword);
    }

    /**
     * 验证密码是否正确
     *
     * @param rawPassword    明文密码
     * @param hashedPassword 哈希后的密码（数据库存储）
     * @return true-密码正确，false-密码错误
     */
    public static boolean verify(String rawPassword, String hashedPassword) {
        try {
            return ARGON2_INSTANCE.verify(hashedPassword, rawPassword);
        } catch (Exception e) {
            log.error("密码验证失败: {}", e.getMessage());
            return false;
        }
    }
}
