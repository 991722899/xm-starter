package com.xm.starter.request.log.enums;

public enum RequestResponseType {
    /**
     * 表示请求类型
     */
    REQUEST("request"),
    /**
     * 表示响应类型
     */
    RESPONSE("response");

    private final String type;

    RequestResponseType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 根据类型字符串获取枚举实例
     * @param type 类型字符串
     * @return 匹配的枚举实例，如果没有匹配的枚举，则返回null
     */
    public static RequestResponseType fromString(String type) {
        for (RequestResponseType rrt : RequestResponseType.values()) {
            if (rrt.getType().equals(type)) {
                return rrt;
            }
        }
        return null;
    }
}
