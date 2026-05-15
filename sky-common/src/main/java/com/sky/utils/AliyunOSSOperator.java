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
 * 封装了阿里云OSS SDK的文件上传操作，提供简洁的upload()方法
 * 上传流程：获取凭证 → 生成路径 → 创建OSSClient → 上传 → 关闭
 * 文件路径格式：年/月/UUID.扩展名（例如 2026/05/a1b2c3d4-xxxx.jpg）
 */
@Slf4j
@Component
public class AliyunOSSOperator {

    private final AliyunOSSProperties aliyunOSSProperties;

    public AliyunOSSOperator(AliyunOSSProperties aliyunOSSProperties) {
        this.aliyunOSSProperties = aliyunOSSProperties;
    }

    /**
     * 上传文件到阿里云OSS
     * @param content          文件字节数组
     * @param originalFilename 原始文件名（用于获取扩展名）
     * @return 文件的访问URL
     */
    public String upload(byte[] content, String originalFilename) throws Exception {
        // 1.获取OSS配置
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();

        // 2.从环境变量获取访问凭证（AccessKey配置在环境变量中，不写在代码里）
        EnvironmentVariableCredentialsProvider credentialsProvider =
                CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 3.按年月生成目录，UUID生成唯一文件名
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = dir + "/" + newFileName;

        // 4.创建OSSClient
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        // 5.上传文件
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
            log.info("文件上传成功: {}", objectName);
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage(), e);
            throw e;
        } finally {
            ossClient.shutdown();
        }

        // 6.生成访问URL: https://bucketName.endpoint/path
        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }
}
