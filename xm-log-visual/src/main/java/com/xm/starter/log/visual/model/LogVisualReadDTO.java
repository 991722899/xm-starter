package com.xm.starter.log.visual.model;

import lombok.Builder;
import lombok.Data;

@Data
public class LogVisualReadDTO {
    private String level = "INFO";
    private String path;
    private Long offset = 0L;
    private Long len;
}
