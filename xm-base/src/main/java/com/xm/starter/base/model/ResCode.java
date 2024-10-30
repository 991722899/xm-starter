package com.xm.starter.base.model;

import lombok.Data;

/**
* @description：响应码
* @author：陈超超
* @time：2024/6/12 14:30
*/
@Data
public class ResCode {
    public static final String SUCCESS = "0";
    public static final String FAIL = "1";
    public static final String UNAUTHORIZED = "400001";
    public static final String FORBIDDEN = "400002";
}
