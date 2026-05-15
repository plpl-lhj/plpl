package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 阿里云OSS配置属性类
 */
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliyunOSSProperties {
    // OSS服务端点（地域节点），例如 oss-cn-beijing.aliyuncs.com
    private String endpoint;
    // OSS存储桶名称
    private String bucketName;
    // 地域ID，例如 cn-beijing
    private String region;
}
