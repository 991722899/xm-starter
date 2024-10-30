package com.xm.starter.area.config;

import com.xm.starter.area.model.AreaProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.area")
@MapperScan(value = "com.xm.starter.area.mapper")
@ConditionalOnProperty(prefix = "xm.starter.area",name = "enable",havingValue = "true")
@EnableConfigurationProperties(AreaProperties.class)
public class AreaConfiguration {

}
