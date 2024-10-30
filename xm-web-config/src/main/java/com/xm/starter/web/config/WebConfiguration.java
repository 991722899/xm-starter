package com.xm.starter.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.web.config")
@ConditionalOnProperty(prefix = "xm.starter.web.config",name = "enable",havingValue = "true")
public class WebConfiguration {

}
