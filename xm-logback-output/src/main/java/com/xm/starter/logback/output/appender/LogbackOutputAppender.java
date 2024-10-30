package com.xm.starter.logback.output.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.OutputStreamAppender;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xm.starter.logback.output.model.LogbackOutputDTO;
import com.xm.starter.logback.output.model.LogbackOutputProperties;
import com.xm.starter.logback.output.service.LogbackOutputService;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

public class LogbackOutputAppender extends OutputStreamAppender<ILoggingEvent> {
    private LogbackOutputProperties logbackOutputProperties;

    public LogbackOutputAppender(LogbackOutputProperties logbackOutputProperties) {
        this.logbackOutputProperties = logbackOutputProperties;
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String [] beanNames =SpringUtil.getBeanNamesForType(LogbackOutputService.class);
        if(ArrayUtil.isNotEmpty(beanNames)){
            LogbackOutputDTO logbackOutputDTO = new LogbackOutputDTO();
            logbackOutputDTO.setILoggingEvent(eventObject);
            for (String beanName : beanNames) {
                LogbackOutputService logbackOutputService = SpringUtil.getBean(beanName);
                logbackOutputService.output(logbackOutputDTO);
            }
        }
    }

    @Override
    public void start() {
        setOutputStream(new BufferedOutputStream(new ByteArrayOutputStream()));
        super.start();
    }
}
