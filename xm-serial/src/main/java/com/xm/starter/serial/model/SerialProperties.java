package com.xm.starter.serial.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "com.xm.starter.serial")
public class SerialProperties {
    private Boolean enable = false;
}
