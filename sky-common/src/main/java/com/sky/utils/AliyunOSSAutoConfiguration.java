package com.sky.utils;

import com.sky.properties.AliyunOSSProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 阿里云OSS自动配置类
 * Spring Boot启动时自动加载，创建AliyunOSSOperator Bean并注册到IOC容器
 *
 * @AutoConfiguration         — Spring Boot 3.x注解，标记为自动配置类
 * @EnableConfigurationProperties — 启用指定的配置属性类，将其注册为Bean
 */
@AutoConfiguration
@EnableConfigurationProperties(AliyunOSSProperties.class)
public class AliyunOSSAutoConfiguration {

    /**
     * 创建AliyunOSSOperator Bean
     * 当容器中不存在该类型Bean时才创建，允许开发者自定义覆盖
     *
     * @ConditionalOnMissingBean — 条件注解，容器中不存在该类型Bean时才创建
     * @param ossProperties OSS配置属性（自动从yml注入）
     * @return AliyunOSSOperator实例
     */
    @Bean
    @ConditionalOnMissingBean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties ossProperties) {
        return new AliyunOSSOperator(ossProperties);
    }
}
