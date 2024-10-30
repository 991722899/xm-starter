package com.xm.starter.task.model;

import cn.hutool.core.collection.ListUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "xm.starter.task")
public class TaskProperties {
    private Boolean enable = false;
    private String updateStatusTopic;

}
