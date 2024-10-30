package com.xm.starter.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.web.config")
public class WebConfigProperties {
    private Boolean enable = false;
    private Boolean enableJacksonMapper = false;
    private Boolean enableResponseBodyAdvice=false;
    private Boolean enableSwagger=false;
    /**
     * 过滤不需要转为RVO对象返回的请求路径
     */
    private List<String> rvoExcludedPatterns = new ArrayList<>();
}
