package com.xm.starter.business.log.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "xm.starter.business.log")
public class BusinessLogProperties {
    private Boolean enable = false;
}
