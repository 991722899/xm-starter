package com.xm.starter.file.enums;

public enum StorageType {
    /**
     * 本地存储类型
     */
    LOCAL("local"),
    
    /**
     * 阿里云OSS存储类型
     */
    OSS("oss");

    private final String type;

    /**
     * 构造方法，用于初始化存储类型的名称。
     * @param type 存储类型的名称
     */
    StorageType(String type) {
        this.type = type;
    }

    /**
     * 根据类型名称获取对应的StorageType枚举值。
     * @param type 类型名称
     * @return 匹配的StorageType枚举值，如果未找到则返回null
     */
    public static StorageType fromString(String type) {
        for (StorageType storageType : values()) {
            if (storageType.getType().equals(type)) {
                return storageType;
            }
        }
        return null;
    }

    /**
     * 获取存储类型的名称。
     * @return 存储类型的名称
     */
    public String getType() {
        return type;
    }
}
