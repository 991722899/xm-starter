package com.xm.starter.auth.config;

import com.xm.starter.auth.interceptor.AuthInterceptor;
import com.xm.starter.auth.model.AuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(prefix = "xm.starter.auth",name = "enable",havingValue = "true")
@ComponentScan(value = "com.xm.starter.auth")
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfiguration implements WebMvcConfigurer {
    private @Autowired AuthInterceptor authInterceptor;
    private @Autowired AuthProperties authProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**")
                .excludePathPatterns(authProperties.getExcludePathPatterns())
                .order(0);
    }
}
