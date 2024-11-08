package com.xm.starter.slow.sql.config;

import com.xm.starter.slow.sql.model.SlowSqlProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.slow.sql")
@MapperScan(value = "com.xm.starter.slow.sql.mapper")
@ConditionalOnProperty(prefix = "xm.starter.slow.sql",name = "enable",havingValue = "true")
@EnableConfigurationProperties(SlowSqlProperties.class)
public class SlowSqlConfiguration {


}
