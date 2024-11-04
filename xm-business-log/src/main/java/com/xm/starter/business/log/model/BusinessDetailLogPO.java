package com.xm.starter.business.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;
@TableName("sys_business_detail_log")
@Data
public class BusinessDetailLogPO extends BasePO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long logId;
    private Integer operationType;
    private String name;
    private String enName;
    private Integer contentType;
    private String beforeValue;
    private String afterValue;
    private String remark;
    private Long parentId;
}
