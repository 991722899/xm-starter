package com.xm.starter.log.visual.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.log-visual")
public class LogVisualProperties {
    private Boolean enable = false;
    private List<String> path;
    private Long len = 20*1024L;
    private Long maxLen = 1024L*1024L*3;
}
