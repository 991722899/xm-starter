package com.xm.starter.web.config;

import com.xm.starter.base.model.RVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;

@Configuration
@ControllerAdvice
@EnableConfigurationProperties(WebConfigProperties.class)
@ConditionalOnProperty(prefix = "xm.starter.web.config",name = "enableResponseBodyAdvice",havingValue = "true")
public class ResponseBodyAdviceConfiguration implements ResponseBodyAdvice<Object> {
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private @Autowired UrlPathHelper urlPathHelper;
    private @Autowired WebConfigProperties webConfigProperties;

    public ResponseBodyAdviceConfiguration(WebConfigProperties webConfigProperties) {
        //过滤openapi地址，不转为RVO
        webConfigProperties.getRvoExcludedPatterns().add("/v3/**");
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            // 获取请求的实际路径
            String lookupPath = urlPathHelper.getLookupPathForRequest(request);
            for (String pattern : webConfigProperties.getRvoExcludedPatterns()) {
                if (pathMatcher.match(pattern, lookupPath)) {
                    return false; // 请求路径匹配排除模式，不支持此ResponseBodyAdvice
                }
            }
        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (!(body instanceof RVO)) {
            return RVO.success(body);
        }
        return body;
    }
}
