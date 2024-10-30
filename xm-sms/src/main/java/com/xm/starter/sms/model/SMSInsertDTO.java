package com.xm.starter.sms.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Builder
@Data
public class SMSInsertDTO {
    /**
     * 序列号名称
     */
    @NotBlank(message = "序列号名称不能为空")
    private String title;
    /**
     * 序列号KEY
     */
    @NotBlank(message = "序列号KEY不能为空")
    private String serialKey;

    /**
     * 最小值
     */
    @NotBlank(message = "最小值不能为空")
    private Long minValue;
    /**
     * 最大值
     */
    @NotBlank(message = "最大值不能为空")
    private Long maxValue;
    /**
     * 增长大小
     */
    @NotBlank(message = "增长大小不能为空")
    private Long stepValue;
    /**
     * 模板
     */
    @NotBlank(message = "模板不能为空")
    /**
     * 备注
     */
    private String remarks;
}
