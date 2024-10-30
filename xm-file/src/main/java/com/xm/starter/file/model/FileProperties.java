package com.xm.starter.file.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xm.starter.file")
public class FileProperties {
    private Boolean enable = false;
    private LocalFileConfig localFileConfig;
    private OssFileConfig ossFileConfig;

    @Data
    public static class LocalFileConfig{
        private String domain;
        private String rootPath;
    }

    @Data
    public static class OssFileConfig{
        private String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        private String accessKeyId;
        private String accessKeySecret;
        private String bucketName;
        private Long urlExpirationMillisecond = 3*365*24*60*60*1000L;
    }
}
