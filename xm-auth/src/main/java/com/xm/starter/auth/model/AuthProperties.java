package com.xm.starter.auth.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.auth")
public class AuthProperties {
    private Boolean enable = false;
    private List<String> excludePathPatterns = new ArrayList<>();
    private String jwtSecret = "abcdefg";
    private Long expirationTime = 24*60*60L;
    private String tokenHeader = "Authorization";
}
