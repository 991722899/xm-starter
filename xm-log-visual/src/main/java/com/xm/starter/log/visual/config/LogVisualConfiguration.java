package com.xm.starter.log.visual.config;

import com.xm.starter.log.visual.model.LogVisualProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.log.visual")
@ConditionalOnProperty(prefix = "xm.starter.log-visual",name = "enable",havingValue = "true")
@EnableConfigurationProperties(LogVisualProperties.class)
public class LogVisualConfiguration {

}
