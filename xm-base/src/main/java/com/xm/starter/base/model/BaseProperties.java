package com.xm.starter.base.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "xm.starter.base")
@Data
public class BaseProperties {

}
