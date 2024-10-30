package com.xm.starter.request.log.enums;

/**
 * 枚举表示系统的状态，包括正常和异常两种情况。
 */
public enum RequestLogStatusEnum {
    /**
     * 表示系统状态正常。
     */
    NORMAL("normal"),
    
    /**
     * 表示系统出现异常状态。
     */
    EXCEPTION("exception");

    private final String status;

    RequestLogStatusEnum(String status) {
        this.status = status;
    }

    /**
     * 获取状态的文字描述。
     * 
     * @return 状态的字符串表示。
     */
    public String getStatus() {
        return status;
    }

    /**
     * 从字符串值获取对应的StatusEnum枚举值。
     * 
     * @param status 状态的字符串表示。
     * @return 匹配的StatusEnum枚举值，如果未找到匹配项则返回null。
     */
    public static RequestLogStatusEnum fromString(String status) {
        for (RequestLogStatusEnum s : RequestLogStatusEnum.values()) {
            if (s.getStatus().equals(status)) {
                return s;
            }
        }
        return null;
    }
}
