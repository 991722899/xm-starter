package com.xm.starter.request.log.model;

import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.request.log")
public class RequestLogProperties {
    private Boolean enable = false;
    private List<String> filterUrlPatterns = new ArrayList<>(ListUtil.toList("/*"));
    private List<String> excludeUrlPatterns;
    private String filterName = "requestLogFilter";
    private Integer filterOrder = 0;
}
