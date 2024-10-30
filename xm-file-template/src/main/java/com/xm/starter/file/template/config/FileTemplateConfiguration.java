package com.xm.starter.file.template.config;

import com.xm.starter.file.template.model.FileTemplateProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.file.template")
@MapperScan(value = "com.xm.starter.file.template.mapper")
@ConditionalOnProperty(prefix = "xm.starter.fileTemplate",name = "enable",havingValue = "true")
@EnableConfigurationProperties(FileTemplateProperties.class)
public class FileTemplateConfiguration {

}
