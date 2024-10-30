package com.xm.starter.area.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "com.xm.starter.area")
public class AreaProperties {
    private Boolean enable = false;
}
