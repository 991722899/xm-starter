package com.xm.starter.business.log.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
/**
* @description：合并后的节点信息（对比前后对象）
* @author：陈超超
* @time：2024/11/4 10:06
*/
@Data
public class BusinessLogNodeInfo {
    private String businessId;
    private String businessNo;
    private JsonNode before;
    private JsonNode after;
    /**
     * 100新增 200修改 300删除
     */
    private Integer mark;
}
