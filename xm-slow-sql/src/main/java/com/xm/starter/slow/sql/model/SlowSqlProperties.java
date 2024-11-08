package com.xm.starter.slow.sql.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.slow.sql")
public class SlowSqlProperties {
    private Boolean enable = false;
    private Long slowSqlTime = 0L;
    private List<String> excludeMapperId;
}
