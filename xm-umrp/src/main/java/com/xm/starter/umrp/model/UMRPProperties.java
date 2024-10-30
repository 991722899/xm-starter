package com.xm.starter.umrp.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xm.starter.rmrp")
public class UMRPProperties {
    private Boolean enable = false;
}
