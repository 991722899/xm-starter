package com.xm.starter.variable.model;

import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
public class VariablePO extends BasePO {
    /**
     * 序列号名称
     */
    private String title;
    /**
     * 序列号KEY
     */
    private String serialKey;
    /**
     * 当前值
     */
    private Long currentValue;
    /**
     * 最小值
     */
    private Long minValue;
    /**
     * 最大值
     */
    private Long maxValue;
    /**
     * 增长大小
     */
    private Long stepValue;
    /**
     * 模板
     */
    private String template;
    /**
     * 模板值，用来控制重置当前值，如一个月内自增
     */
    private String templateValue;
    /**
     * 备注
     */
    private String remarks;
}
