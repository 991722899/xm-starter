package com.xm.starter.serial.config;

import com.xm.starter.serial.model.SerialProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.serial")
@MapperScan(value = "com.xm.starter.serial.mapper")
@ConditionalOnProperty(prefix = "xm.starter.serial",name = "enable",havingValue = "true")
@EnableConfigurationProperties(SerialProperties.class)
public class SerialConfiguration {

}
