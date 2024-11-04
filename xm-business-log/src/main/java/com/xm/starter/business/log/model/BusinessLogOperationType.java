package com.xm.starter.business.log.model;

public enum BusinessLogOperationType {
    //操作类型 100添加 200修改 300删除 400导出
    ADD("100","添加"),
    UPDATE("200","修改"),
    DELETE("300","删除"),
    EXPORT("400","导出")
    ;
    private String code;
    private String name;

    BusinessLogOperationType(String code, String name) {
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
