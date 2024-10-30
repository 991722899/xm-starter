package com.xm.starter.task.model.vo;

import com.xm.starter.task.model.po.TaskConfigPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TaskConfigListVO extends TaskConfigPO {
    /**
     * 任务编码
     */
    @Schema(description = "任务编码")
    private String code;
    /**
     * 任务名称
     */
    @Schema(description = "任务名称")
    private String name;
    /**
     * bean名称
     */
    @Schema(description = "bean名称")
    private String beanName;
    /**
     * 运行类型 100同步 200异步
     */
    @Schema(description = "运行类型 100同步 200异步")
    private Integer runType;
    /**
     * 组编码
     */
    @Schema(description = "组编码")
    private String groupCode;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;
}
