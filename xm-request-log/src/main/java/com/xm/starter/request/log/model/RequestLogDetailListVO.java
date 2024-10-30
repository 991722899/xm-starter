package com.xm.starter.request.log.model;

import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
public class RequestLogDetailListVO extends BasePO {
    /**
     * 日志ID
     */
    private Long requestLogId;
    /**
     * request请求，response响应
     */
    private String type;
    /**
     * header,content
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
