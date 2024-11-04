package com.xm.starter.business.log.formatter;

import cn.hutool.core.util.ObjectUtil;

public class BaseFormatter implements BusinessLogItemFormatter{
    @Override
    public String format(Object o) {
        return ObjectUtil.defaultIfNull(o,"").toString();
    }
}
