package com.xm.starter.request.log.enums;

public enum ContentType {

    /**
     * 请求头或响应头
     */
    HEADER("header"),

    /**
     * 请求体或响应体
     */
    CONTENT("content"),
    /**
     * 请求参数
     */
    PARAMETER("parameter");

    private final String type;

    ContentType(String type) {
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
    public static ContentType fromString(String type) {
        for (ContentType rrt : ContentType.values()) {
            if (rrt.getType().equals(type)) {
                return rrt;
            }
        }
        return null;
    }
}
