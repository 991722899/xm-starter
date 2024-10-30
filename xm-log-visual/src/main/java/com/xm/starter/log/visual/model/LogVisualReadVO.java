package com.xm.starter.log.visual.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LogVisualReadVO {
    private Long offset;
    private List<FileInfo> fileInfos;
    private String logContent;
    private int size;

    @Data
    public static class  FileInfo{
        private LocalDateTime modifyTime;
        private String path;
        private String name;
    }
}
