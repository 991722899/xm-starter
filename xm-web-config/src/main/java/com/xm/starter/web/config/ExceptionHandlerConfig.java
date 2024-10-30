package com.xm.starter.web.config;

import com.xm.starter.base.exception.BaseException;
import com.xm.starter.base.model.RVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Configuration
@ControllerAdvice
@ConditionalOnProperty(prefix = "com.xm.starter.web.config",name = "enableExceptionHandler",havingValue = "true")
public class ExceptionHandlerConfig {
    @ExceptionHandler(value = {BaseException.class})
    @ResponseBody
    public RVO exceptionHandler(BaseException e){
        log.error("HandlerException",e);
        return RVO.fail(e.getMessage());
    }


    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public RVO exceptionHandler(Exception e){
        log.error("HandlerException",e);
        if(e instanceof InvocationTargetException){
            log.error("HandlerException target",((InvocationTargetException) e).getTargetException());
            return new RVO(null,((InvocationTargetException) e).getTargetException().getMessage(),null).fail();
        }
        return RVO.fail(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public RVO exceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);

        String errorInfo = "";
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            errorInfo = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        }

        return RVO.fail(errorInfo);
    }
}
