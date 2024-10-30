package com.xm.starter.task.model.dto;

import com.xm.starter.task.model.vo.TaskConfigDetailVO;
import com.xm.starter.task.model.vo.TaskDetailVO;
import lombok.Data;

@Data
public class TaskDTO<T> {
    /**
     * 请求参数
     */
    private T params;

    /**
     * 配置信息
     */
    private TaskConfigDetailVO taskConfig;

    /**
     * 任务信息
     */
    private TaskDetailVO task;
}
