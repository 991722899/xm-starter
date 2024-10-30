package com.xm.starter.sms.enums;

public enum SmsSendStatus {
    // 待发送状态
    PENDING(100, "待发送"),
    // 发送成功状态
    SUCCESS(200, "发送成功"),
    // 发送失败状态
    FAILURE(300, "发送失败");

    // 枚举实例变量
    private final int code;
    private final String description;

    // 构造函数
    SmsSendStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    // 获取枚举的code值
    public int getCode() {
        return code;
    }

    // 获取枚举的描述信息
    public String getDescription() {
        return description;
    }

    // 根据code获取枚举对象
    public static SmsSendStatus getByCode(int code) {
        for (SmsSendStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
