package com.xm.starter.business.log.model;

import lombok.Data;

/**
* @description：内容类型
* @author：陈超超
* @time：2024/11/1 11:48
*/
public enum ItemContentType {
    //内容类型 100文本 200文本域 300数字 400布尔值 500日期 600图片 700文件 800日期时间
    TEXT("文本",100),
    TEXTAREA("文本域",200),
    NUMBER("数字",300),
    BOOLEAN("布尔值",400),
    DATE("日期",500),
    IMAGE("图片",600),
    FILE("文件",700),
    DATE_TIME("日期时间",800);

    ItemContentType(String name, Integer code) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
