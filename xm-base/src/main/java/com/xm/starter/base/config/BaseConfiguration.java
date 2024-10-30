package com.xm.starter.base.config;

import com.xm.starter.base.model.BaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(BaseProperties.class)
@Configuration
public class BaseConfiguration {

}
