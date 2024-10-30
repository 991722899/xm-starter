package com.xm.starter.file.enums;

public enum MainFileStatus {
    NO(0, "否"),
    YES(1, "是");

    private final Integer value;
    private final String description;

    MainFileStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    // 通过值获取枚举实例
    public static MainFileStatus fromValue(Integer value) {
        for (MainFileStatus status : MainFileStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("未知的MainFileStatus值: " + value);
    }
}
