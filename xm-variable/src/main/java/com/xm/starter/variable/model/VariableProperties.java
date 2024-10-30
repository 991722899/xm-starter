package com.xm.starter.variable.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "com.xm.starter.serial")
public class VariableProperties {
    private Boolean enable = false;
}
