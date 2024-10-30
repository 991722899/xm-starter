package com.xm.starter.variable.config;

import com.xm.starter.variable.model.VariableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.variable")
@MapperScan(value = "com.xm.starter.variable.mapper")
@ConditionalOnProperty(prefix = "xm.starter.variable",name = "enable",havingValue = "true")
@EnableConfigurationProperties(VariableProperties.class)
public class VariableConfiguration {

}
