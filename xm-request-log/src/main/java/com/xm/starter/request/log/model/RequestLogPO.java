package com.xm.starter.request.log.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_request_log")
public class RequestLogPO extends BasePO {
    /**
     * 标题
     */
    private String title;
    /**
     * 状态，正常normal，异常exception
     */
    private String status;
    /**
     * 耗时，毫秒
     */
    private Long timeConsuming;
    /**
     * 异常详情
     */
    private String exceptionDetail;

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
