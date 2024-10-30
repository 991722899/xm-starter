package com.xm.starter.dict.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.xm.starter.dict")
@MapperScan(value = "com.xm.starter.dict.mapper")
public class DictConfiguration {

}
