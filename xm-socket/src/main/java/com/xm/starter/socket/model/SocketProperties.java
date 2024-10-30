package com.xm.starter.socket.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "xm.starter.socket")
public class SocketProperties {
    private Boolean enable = false;
    private String host = "127.0.0.1";
    private Integer port = 8080;


}
