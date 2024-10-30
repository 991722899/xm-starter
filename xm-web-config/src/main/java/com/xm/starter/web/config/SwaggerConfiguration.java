package com.xm.starter.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "xm.starter.web.config",name = "enableSwagger",havingValue = "true")
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("我的API文档")
                .description("这是我的应用程序API文档的自定义描述。")
                .version("1.0")
                .contact(new Contact().name("API 支持").email("support@example.com"))
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**") // 根据实际情况匹配您的API路径
                .build();
    }
}
