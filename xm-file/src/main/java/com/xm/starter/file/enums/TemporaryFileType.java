package com.xm.starter.file.enums;
public enum TemporaryFileType {
    TEMPORARY(0, "临时文件"),
    NON_TEMPORARY(1, "非临时文件");

    private final Integer value;
    private final String description;

    TemporaryFileType(Integer value, String description) {
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
    public static TemporaryFileType fromValue(Integer value) {
        for (TemporaryFileType type : TemporaryFileType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的TemporaryFileType值: " + value);
    }
}
