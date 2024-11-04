package com.xm.starter.business.log.model;

import lombok.Data;

/**
* @description：代码手动添加业务操作日志
* @author：陈超超
* @time：2024/11/4 10:00
*/
@Data
public class BusinessLogManualInsertDto {
    private String ip;
    private String remark;
    /**
     * 操作路径使用:表示多层级
     */
    private String path;
    private String createUserName;
    private String updateUserName;
    private BusinessLogOperationType operationType = BusinessLogOperationType.UPDATE;
}
