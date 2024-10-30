package com.xm.starter.umrp.enums;

public enum MenuTypeEnum {
    MENU(100, "菜单"),
    BUTTON(200, "按钮"),
    API(300, "接口");

    private Integer code;
    private String description;

    MenuTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    // 根据code值获取对应的枚举类型，考虑添加这个静态方法便于在其它地方使用
    public static MenuTypeEnum getByCode(Integer code) {
        for (MenuTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
