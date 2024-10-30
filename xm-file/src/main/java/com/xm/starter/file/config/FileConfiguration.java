package com.xm.starter.file.config;

import com.xm.starter.file.model.FileProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.file")
@MapperScan(value = "com.xm.starter.file.mapper")
@EnableConfigurationProperties(FileProperties.class)
@ConditionalOnProperty(prefix = "xm.starter.file",name = "enable",havingValue = "true")
public class FileConfiguration {

}
