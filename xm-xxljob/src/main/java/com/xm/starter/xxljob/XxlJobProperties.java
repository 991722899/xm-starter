package com.xm.starter.xxljob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = XxlJobProperties.PREFIX)
@Data
public class XxlJobProperties {
    public static final String PREFIX = "xm.starter.xxljob";
    private Boolean enable = false;
    private String accessToken;
    private XxlJobProperties.Admin admin = new XxlJobProperties.Admin();
    private XxlJobProperties.Executor executor = new XxlJobProperties.Executor();

    @Data
    public class Admin{
        private String address;
    }

    @Data
    public class Executor{
        private String appName;
        private String address;
        private String ip;
        private Integer port;
        private String logPath;
        private Integer logRetentionDays =30;
    }

}
