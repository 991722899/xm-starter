package com.xm.starter.task.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;
@TableName("sys_task_config")
@Data
public class TaskConfigPO extends BasePO {
    /**
     * 任务编码
     */
    private String code;
    /**
     * 任务名称
     */
    private String name;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 运行类型 100同步 200异步
     */
    private Integer runType;
    /**
     * 组编码
     */
    private String groupCode;
    /**
     * 备注
     */
    private String remarks;
}
