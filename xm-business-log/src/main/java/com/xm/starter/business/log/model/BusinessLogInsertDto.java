package com.xm.starter.business.log.model;

import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
public class BusinessLogInsertDto extends BasePO {
    private String businessId;
    private String businessNo;
    private String remark;
    private String ip;
    private BusinessLogOperationType operationType;
    private String operationName;
    /**
     * 操作路径使用:表示多层级
     */
    private String path;
}
