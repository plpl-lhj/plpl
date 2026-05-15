package com.sky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot应用启动类
 *
 * @SpringBootApplication — 核心注解，组合了以下三个注解：
 *   - @Configuration：标记为配置类
 *   - @EnableAutoConfiguration：启用Spring Boot自动配置机制
 *   - @ComponentScan：扫描当前包及子包下的@Component、@Service、@Controller等注解
 */
@SpringBootApplication
public class SkyServerApplication {

    /**
     * 应用入口方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SkyServerApplication.class, args);
    }
}
