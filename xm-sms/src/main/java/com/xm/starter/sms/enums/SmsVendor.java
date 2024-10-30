package com.xm.starter.sms.enums;

public enum SmsVendor {
    ALIYUN("aliyun");

    private final String vendorName;

    SmsVendor(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName() {
        return vendorName;
    }

    // 如果需要根据vendorName获取枚举对象，可以添加如下方法
    public static SmsVendor getByVendorName(String vendorName) {
        for (SmsVendor vendor : values()) {
            if (vendor.getVendorName().equals(vendorName)) {
                return vendor;
            }
        }
        throw new IllegalArgumentException("Invalid vendor name: " + vendorName);
    }
}
