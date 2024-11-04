package com.xm.starter.business.log.formatter;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;

import java.math.BigDecimal;
/**
* @description：指定小娄位数格式化
* @author：陈超超
* @time：2024/11/1 11:34
*/
public class BigDecimalFormatter implements BusinessLogItemFormatter {
    private Integer scale = 2;

    public BigDecimalFormatter() {
    }

    private BigDecimalFormatter(Integer scale){
        this.scale = scale;
    }

    @Override
    public String format(Object o) {
        if(NumberUtil.isNumber(ObjectUtil.defaultIfNull(o,"").toString())){
            return new BigDecimal(o.toString()).setScale(scale,BigDecimal.ROUND_HALF_UP).toString();
        }
        return ObjectUtil.defaultIfNull(o,"").toString();
    }
}
