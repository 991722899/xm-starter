package com.xm.starter.sms.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SMSSendDTO {
    private String phone;
    private String templateCode;
    private Map<String,Object> templateParam;
    private String signName;
}
