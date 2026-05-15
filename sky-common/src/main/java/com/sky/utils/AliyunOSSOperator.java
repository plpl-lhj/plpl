package com.sky.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.sky.properties.AliyunOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 阿里云OSS文件上传操作类
 * 封装OSS SDK的文件上传操作，提供简洁的upload()方法
 *
 * 上传流程：获取凭证 → 生成唯一文件路径 → 创建OSSClient → 上传 → 关闭连接
 * 文件路径格式：yyyy/MM/UUID.扩展名（如2026/05/a1b2c3d4-xxxx.jpg）
 */
@Slf4j
@Component
public class AliyunOSSOperator {

    private final AliyunOSSProperties ossProperties;

    /**
     * 构造函数注入
     *
     * @param ossProperties OSS配置属性
     */
    public AliyunOSSOperator(AliyunOSSProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    /**
     * 上传文件到阿里云OSS
     *
     * @param fileBytes       文件字节数组
     * @param originalFilename 原始文件名（用于提取扩展名）
     * @return 文件的访问URL
     * @throws Exception 上传失败时抛出异常
     */
    public String upload(byte[] fileBytes, String originalFilename) throws Exception {
        // 1. 获取OSS配置
        String endpoint = ossProperties.getEndpoint();
        String bucketName = ossProperties.getBucketName();
        String region = ossProperties.getRegion();

        // 2. 从环境变量获取访问凭证（避免硬编码AccessKey）
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 3. 生成唯一文件路径：yyyy/MM/UUID.扩展名
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = datePath + "/" + UUID.randomUUID() + extension;

        // 4. 创建OSSClient实例
        ClientBuilderConfiguration clientConfig = new ClientBuilderConfiguration();
        clientConfig.setSignatureVersion(SignVersion.V4);

        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientConfig)
                .region(region)
                .build();

        // 5. 上传文件并处理异常
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(fileBytes));
            log.info("文件上传成功: {}", objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw e;
        } finally {
            ossClient.shutdown();
        }

        // 6. 拼接并返回访问URL
        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }
}
