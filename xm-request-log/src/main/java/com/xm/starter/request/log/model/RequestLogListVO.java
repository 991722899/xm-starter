package com.xm.starter.request.log.model;

import lombok.Data;

@Data
public class RequestLogListVO {
    /**
     * ip
     */
    private String ip;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
}
