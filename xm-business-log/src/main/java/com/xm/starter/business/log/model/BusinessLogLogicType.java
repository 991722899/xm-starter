package com.xm.starter.business.log.model;

import lombok.Data;

/**
* @description：逻辑处理类型
* @author：陈超超
* @time：2024/11/1 11:51
*/
public enum BusinessLogLogicType {
    COMPARE("COMPARE","比对"),
    NO_COMPARE("NO_COMPARE","不比对"),
    CHILD_CHANGE("CHILD_CHANGE","子集有变更时记录");


    private String code;
    private String name;

    BusinessLogLogicType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
