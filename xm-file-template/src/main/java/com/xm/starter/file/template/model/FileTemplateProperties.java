package com.xm.starter.file.template.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xm.starter.fileTemplate")
public class FileTemplateProperties {
    private Boolean enable = false;
}
