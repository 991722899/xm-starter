package com.xm.starter.task;

import com.xm.starter.base.exception.BaseException;

public class TaskNotFoundException extends BaseException {
    public TaskNotFoundException() {
        super();
    }

    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, String code) {
        super(message, code);
    }

    public TaskNotFoundException(String message, Throwable cause, String code) {
        super(message, cause, code);
    }

    public TaskNotFoundException(Throwable cause, String code) {
        super(cause, code);
    }

    public TaskNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TaskNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
