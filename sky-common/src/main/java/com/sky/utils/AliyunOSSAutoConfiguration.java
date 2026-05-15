package com.sky.utils;

import com.sky.properties.AliyunOSSProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 阿里云OSS自动配置类
 * @AutoConfiguration — 标记为自动配置类，Spring Boot启动时自动加载
 * @EnableConfigurationProperties — 启用AliyunOSSProperties配置绑定
 * 作用：自动创建AliyunOSSOperator并放入IOC容器，开发者只需在yml中配置OSS属性
 */
@AutoConfiguration
@EnableConfigurationProperties(AliyunOSSProperties.class)
public class AliyunOSSAutoConfiguration {

    /**
     * 创建AliyunOSSOperator的Bean
     * @ConditionalOnMissingBean — 当容器中不存在时才会创建，允许开发者覆盖默认配置
     * @param aliyunOSSProperties OSS配置属性（自动从yml注入）
     * @return AliyunOSSOperator实例
     */
    @Bean
    @ConditionalOnMissingBean
    public AliyunOSSOperator aliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties) {
        return new AliyunOSSOperator(aliyunOSSProperties);
    }
}
