package com.xm.starter.task.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("sys_task")
@Data
public class TaskPO extends BasePO {
    /**
     * 配置ID
     */
    private Long configId;
    /**
     * 任务名称或文件名称
     */
    private String name;
    /**
     * 状态 100待执行 200执行中 300执行成功 400执行失败
     * {@link com.xm.starter.task.enums.TaskStatus}
     */
    private Integer status;
    /**
     * 开始时间
     */
    private LocalDateTime beginTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 请求参数
     */
    private String params;
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
     * 消息ID
     */
    private String msgId;
    /**
     * 备注
     */
    private String remarks;
}
