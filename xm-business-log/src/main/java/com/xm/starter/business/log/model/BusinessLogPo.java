package com.xm.starter.business.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;
@TableName("sys_business_log")
@Data
public class BusinessLogPo extends BasePO {
    private String businessId;
    private String businessNo;
    private String remark;
    private String ip;
    private String batchNo;
    private Integer operationType;
    private String operationName;
    private String path;
}
