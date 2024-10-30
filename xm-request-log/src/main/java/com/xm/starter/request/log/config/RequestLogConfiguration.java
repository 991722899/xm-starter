package com.xm.starter.request.log.config;

import com.xm.starter.request.log.model.RequestLogProperties;
import com.xm.starter.request.log.service.RequestLogOutputService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@ComponentScan(value = "com.xm.starter.request.log")
@MapperScan(value = "com.xm.starter.request.log.mapper")
@ConditionalOnProperty(prefix = "xm.starter.request.log",name = "enable",havingValue = "true")
@EnableConfigurationProperties(RequestLogProperties.class)
public class RequestLogConfiguration {

    @Bean
    public FilterRegistrationBean requestLogFilter(RequestLogProperties requestLogProperties, RequestLogOutputService requestLogOutputService, UrlPathHelper urlPathHelper){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setUrlPatterns(requestLogProperties.getFilterUrlPatterns());
        filterRegistrationBean.setName(requestLogProperties.getFilterName());
        filterRegistrationBean.setOrder(requestLogProperties.getFilterOrder());
        filterRegistrationBean.setFilter(new RequestLogFilter(requestLogOutputService,requestLogProperties,urlPathHelper));
        return filterRegistrationBean;
    }
}
