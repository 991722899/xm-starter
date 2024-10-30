package com.xm.starter.task.config;

import com.xm.starter.task.listener.TaskListener;
import com.xm.starter.task.model.TaskProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "xm.starter.task",name = "enable",havingValue = "true")
@ComponentScan(value = "com.xm.starter.task")
@MapperScan(value = "com.xm.starter.task.mapper")
@Configuration
@EnableConfigurationProperties(value = TaskProperties.class)
public class TaskConfiguration {

}
