package com.xm.starter.request.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_request_log_detail")
public class RequestLogDetailPO extends BasePO {
    /**
     * 日志ID
     */
    private Long requestLogId;
    /**
     * request请求，response响应
     */
    private String type;
    /**
     * 内容类型  header头 content内容
     */
    private String contentType;
    /**
     * key
     */
    private String contentKey;
    /**
     * value
     */
    private String contentValue;
}
