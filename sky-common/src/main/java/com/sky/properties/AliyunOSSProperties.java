package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云OSS配置属性类
 * 通过@ConfigurationProperties绑定application.yml中aliyun.oss开头的配置项
 *
 * @ConfigurationProperties — Spring Boot注解，将配置文件中的属性映射到Java对象
 *                           prefix属性：指定配置项的前缀
 */
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {

    /**
     * OSS服务端点（地域节点）
     * 示例：https://oss-cn-beijing.aliyuncs.com
     */
    private String endpoint;

    /**
     * OSS存储桶名称
     * 每个Bucket是独立的存储空间
     */
    private String bucketName;

    /**
     * 地域ID（与endpoint对应）
     * 示例：cn-beijing
     */
    private String region;
}
