package com.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @SpringBootApplication - 这是一个Spring Boot应用的入口注解
 * 它组合了以下三个注解：
 * - @Configuration：标记该类为配置类
 * - @EnableAutoConfiguration：启用自动配置
 * - @ComponentScan：扫描当前包及子包下的组件
 */
@SpringBootApplication
public class SkyServerApplication {

    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(SkyServerApplication.class, args);
    }

}
