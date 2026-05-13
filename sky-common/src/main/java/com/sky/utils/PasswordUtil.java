package com.sky.utils;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.extern.slf4j.Slf4j;

/**
 * 密码工具类
 * 使用Argon2算法对密码进行哈希和验证
 * Argon2 - 2015年Password Hashing Competition冠军算法
 *         目前最安全的密码哈希算法之一，抗GPU/ASIC攻击
 */
@Slf4j
public final class PasswordUtil {
    // 私有构造函数，防止实例化
    private PasswordUtil() {
    }

    /**
     * Argon2实例
     * Argon2Factory.create() - 创建Argon2哈希实例
     * Argon2Types.ARGON2id - Argon2的变体
     *   - Argon2d：GPU/ASIC抗性最强，但可能受侧信道攻击
     *   - Argon2i：侧信道安全，但GPU抗性较弱
     *   - Argon2id：兼顾两者安全性，推荐使用
     */
    private static final Argon2 INSTANCE = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

    // 迭代次数 - 哈希计算的运行次数
    private static final int ITERATIONS = 3;
    // 内存使用量 - 64 MB，抵御内存攻击
    private static final int MEMORY = 64 * 1024;
    // 并行度 - 使用的线程数/哈希次数
    private static final int PARALLELISM = 4;

    /**
     * 对密码进行哈希
     * @param rawPassword 原始密码(明文)
     * @return 哈希后的密码字符串
     */
    public static String hash(String rawPassword) {
        // INSTANCE.hash() - 生成密码哈希
        // 参数：迭代次数、内存大小、并行度、原始密码
        // 返回： Argon2id$version=$m=,t=,p=,salt$,hash 格式的字符串
        return INSTANCE.hash(ITERATIONS, MEMORY, PARALLELISM, rawPassword);
    }

    /**
     * 验证密码是否正确
     * @param rawPassword 原始密码(明文)
     * @param hashedPassword 哈希后的密码(数据库中存储的)
     * @return true-密码正确，false-密码错误
     */
    public static boolean verify(String rawPassword, String hashedPassword) {
        try {
            // INSTANCE.verify() - 验证密码
            // 自动解析哈希字符串中的参数进行验证
            return INSTANCE.verify(hashedPassword, rawPassword);
        } catch (Exception e) {
            // 验证失败(哈希格式错误、salt损坏等)返回false
            log.error("密码验证失败: {}", e.getMessage());
            return false;
        }
    }
}
