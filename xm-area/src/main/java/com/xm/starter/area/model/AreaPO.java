package com.xm.starter.area.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * https://github.com/adyliu/china_area
 */
@TableName("sys_area")
@Data
public class AreaPO{
    /**
     * 区划代码
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long code;
    /**
     * 名称
     */
    private String name;
    /**
     * 级别1-5,省市县镇村
     */
    private Integer level;
    /**
     * 父级区划代码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pcode;
    /**
     * 城乡分类
     */
    private Integer category;

}
