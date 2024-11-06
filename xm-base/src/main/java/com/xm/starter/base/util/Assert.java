package com.xm.starter.base.util;

import com.xm.starter.base.exception.BaseException;
import com.xm.starter.base.exception.ServiceException;

public class Assert {
    public static void isTrue(boolean expression, String message) throws BaseException {
        if (!expression) {
            throw new ServiceException(message);
        }
    }
    public static void isTrue(boolean expression, RuntimeException exception) throws BaseException {
        if (!expression) {
           throw exception;
        }
    }
}
