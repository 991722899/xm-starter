package com.xm.starter.umrp.config;

import com.xm.starter.umrp.model.UMRPProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.umrp")
@MapperScan(value = "com.xm.starter.umrp.mapper")
@ConditionalOnProperty(prefix = "xm.starter.umrp",name = "enable",havingValue = "true")
@EnableConfigurationProperties(UMRPProperties.class)
public class UMRPConfiguration {

}
