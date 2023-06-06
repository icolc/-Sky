package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NXRUI
 * @version 1.0
 * @date 2023/5/23 8:44
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliOSSProperties {
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
