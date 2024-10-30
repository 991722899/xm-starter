package com.xm.starter.task.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskEven {
    /**
     * 任务ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;
    /**
     * 状态 100待执行 200执行中 300执行成功 400执行异常 500部分成功
     * {@link com.xm.starter.task.enums.TaskStatus}
     */
    private Integer status;

    /**
     * 下载地址
     */
    private String downloadUrl;
    /**
     * 下载批次号
     */
    private String downloadBatchNo;
    /**
     * 异常信息
     */
    private String exceptionMsg;

    /**
     * 开始时间
     */
    private LocalDateTime createTime;
}
