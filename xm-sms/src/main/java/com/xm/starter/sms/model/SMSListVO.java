package com.xm.starter.sms.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SMSListVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private String variable_key;
    private String variable_value;
    private String remarks;
}
