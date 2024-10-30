package com.xm.starter.rocketmq.model;

import lombok.Data;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;
@Data
@ConfigurationProperties(prefix = "xm.starter.rocketmq")
public class RocketmqProperties {
    private Boolean enable = false;
    private Map<String,ConsumeConfig> consumeConfig = new LinkedHashMap<>();



    @Data
    public static class ConsumeConfig{
        private String beanName;
        private Integer threadMax=5;
        private Integer threadNumber=3;
        private String topic;
        private String group;
        private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;
        private MessageModel messageModel = MessageModel.CLUSTERING;
        private Integer maxReconsumeTimes = -1;
        private Long timeout = 15L;
        private String secretKey;
        private String accessKey;
        private String namespace;
        private Boolean enableMsgTrace = false;
        private String customizedTraceTopic="";
        private String nameServer = "${rocketmq.name-server:}";
        private String accessChannel = "${rocketmq.access-channel:}";
        private Long awaitTerminationMillisWhenShutdown =1000L;
        private SelectorType selectorType = SelectorType.TAG;
        private String selectorExpression = "*";
        private Boolean tlsEnable = false;
    }
}
