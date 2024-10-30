package com.xm.starter.logback.output.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.logback.output")
public class LogbackOutputProperties {
    private Boolean enable = false;
    private String pattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger - %msg%n";
    private String charset="UTF-8";
    private String appenderName = "LOGBACK-OUTPUT-APPENDER";

    private DingDingConfig dingDingConfig = new DingDingConfig();
    private XxlJobConfig xxlJobConfig = new XxlJobConfig();


    /**
     * 钉钉通知
     */
    @Data
    public static class DingDingConfig{
        private Boolean enable = false;
        /**
         * 每分钟最大发送信息条数，钉钉限制是18
         */
        private Long perMinute = 10L;

        /**
         * 机器人地址
         */
        private String dingTalkReboot;

        /**
         * 发送内容最大长度（字节），超出会被截断发送
         */
        private Integer contextMaxLength = 18000;

        /**
         * 不需要推送的异常
         */
        private List<String> excludeExceptions = new ArrayList<>();
    }

    /**
     * xxl-job日志输出
     */
    @Data
    public static class XxlJobConfig{
        private Boolean enable = false;

    }
}
