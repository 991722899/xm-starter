package com.xm.starter.business.log.model;

import com.xm.starter.business.log.service.BusinessLogHandler;

import java.lang.annotation.*;
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessLog {
        Class<? extends BusinessLogHandler> value();
        String path() default "";
        BusinessLogOperationType operationType() default BusinessLogOperationType.ADD;
        String paramsScript() default "";
        String returnScript() default "";

}
