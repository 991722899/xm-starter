package com.xm.starter.socket.enums;

public enum LoginStatus {
    SUCCESS(100, "登录成功"),
    FAILURE(200, "登录失败");

    private final int code;
    private final String description;

    LoginStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据状态码获取 LoginStatus 枚举值
     * @param code 状态码
     * @return LoginStatus 枚举值
     */
    public static LoginStatus getByCode(int code) {
        for (LoginStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid login status code: " + code);
    }
}
