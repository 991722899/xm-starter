package com.xm.starter.mybatis.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @description：表基础字段
* @author：陈超超
* @time：2024/6/6 10:17
*/
@Data
public class BasePO {
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer sort;
    @TableField(fill = FieldFill.INSERT,value = "create_id")
    private String createId;
    @TableField(fill = FieldFill.INSERT,value = "create_name")
    private String createName;
    @TableField(fill = FieldFill.INSERT,value = "create_time")
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE,value = "update_id")
    private String updateId;
    @TableField(fill = FieldFill.UPDATE,value = "update_name")
    private String updateName;
    @TableField(fill = FieldFill.UPDATE,value = "update_time")
    private LocalDateTime updateTime;

}
