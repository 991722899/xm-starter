package com.xm.starter.task.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TaskConfigUpdateByIdDTO {
    /**
     * 配置ID
     */
    @NotNull(message = "配置ID不能为空")
    private Long id;
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    private String name;
    /**
     * bean名称
     */
    @NotBlank(message = "bean名称不能为空")
    private String beanName;
    /**
     * 运行类型 100同步 200异步
     */
    @NotNull(message = "运行类型不能为空")
    private Integer runType;
    /**
     * 组编码
     */
    @NotBlank(message = "组编码不能为空")
    private String groupCode;
    /**
     * 备注
     */
    private String remarks;
}
