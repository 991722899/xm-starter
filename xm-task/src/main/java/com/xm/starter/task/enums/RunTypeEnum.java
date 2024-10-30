package com.xm.starter.task.enums;

public enum RunTypeEnum {
    SYNCHRONOUS(100, "同步"),
    ASYNCHRONOUS(200, "异步");

    private final Integer code;
    private final String description;

    RunTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据code值获取对应的枚举值
    public static RunTypeEnum fromCode(Integer code) {
        for (RunTypeEnum type : RunTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
