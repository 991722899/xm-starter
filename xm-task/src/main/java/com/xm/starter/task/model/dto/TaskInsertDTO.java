package com.xm.starter.task.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskInsertDTO {
    private String params;
    @NotBlank(message = "任务CODE不能为空")
    private String code;
}
