package com.xm.starter.sms.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

import java.time.LocalDateTime;
@TableName("sys_sms")
@Data
public class SMSPO extends BasePO {
    /**
     * 短信供应商 aliyun
     */
    private String vendorType;
    /**
     *业务类型
     */
    private String businessType;
    /**
     * 业务ID
     */
    private Long businessId;
    /**
     * 发送回执 ID
     */
    private String bizId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 短信发送状态 100待发送 200发送成功 300发送失败
     */
    private Integer status;
    /**
     * 短信内容
     */
    private String content;
    /**
     * 发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 异常信息
     */
    private String exceptionMsg;
    /**
     * 模板编码
     */
    private String templateCode;
    /**
     * 模板参数
     */
    private String templateParam;
    /**
     * 备注
     */
    private String remarks;

    /**
     * 请求 ID。
     */
    private String requestId;
}
