package com.xm.starter.sms.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "xm.starter.sms")
public class SMSProperties {
    private Boolean enable = false;
    private AliYun aliYun;
    private Boolean activateController  =true;



    @Data
    public static class AliYun{
        private Boolean enable = false;
        private String accessKeyId;
        private String accessKeySecret;
        private String endPoint = "dysmsapi.aliyuncs.com";
    }
}
