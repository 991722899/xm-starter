package com.xm.starter.slow.sql.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "com.xm.starter.slow.sql")
public class SlowSqlProperties {
    private Boolean enable = false;
}
