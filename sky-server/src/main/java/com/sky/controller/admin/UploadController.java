package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliyunOSSOperator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * 文件上传
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Tag(name = "文件上传")
public class UploadController {
    private final AliyunOSSOperator aliyunOSSOperator;

    public UploadController(AliyunOSSOperator aliyunOSSOperator) {
        this.aliyunOSSOperator = aliyunOSSOperator;
    }

    /**
     * 文件上传
     * @param file 前端上传的文件
     * @return 文件访问URL
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传")
    public Result<String> upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file.getOriginalFilename());

        if (file.isEmpty()) {
            log.warn("上传文件为空");
            return Result.error("请选择要上传的文件");
        }

        String url = aliyunOSSOperator.upload(file.getBytes(), Objects.requireNonNull(file.getOriginalFilename()));

        log.info("文件上传成功，URL: {}", url);

        return Result.success(url);
    }
}
