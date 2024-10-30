package com.xm.starter.base.model;

import lombok.Data;

import java.util.List;

/**
* @description：分页  响应给前端数据结构
* @author：陈超超
* @time：2024/6/6 11:44
*/
@Data
public class PageVO<T> {
    private List<T> data;
    private Long total;
    private Long pageNum;
    private Long pageSize;
}
