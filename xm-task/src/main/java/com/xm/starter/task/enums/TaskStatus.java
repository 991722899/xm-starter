package com.xm.starter.task.enums;

public enum TaskStatus {
    PENDING(100, "待执行"),
    IN_PROGRESS(200, "执行中"),
    SUCCESS(300, "执行成功"),
    EXCEPTION(400, "执行失败"),
    PARTIAL_SUCCESS(500, "部分成功");

    private final int code;
    private final String description;

    TaskStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据code值获取对应的枚举项，考虑添加此方法便于在业务中通过状态码获取状态信息
    public static TaskStatus fromCode(int code) {
        for (TaskStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
