package com.xm.starter.business.log.model;

import java.lang.annotation.*;
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessLogs {
    BusinessLog[] logs();
}
