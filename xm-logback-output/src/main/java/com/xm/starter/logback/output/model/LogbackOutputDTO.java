package com.xm.starter.logback.output.model;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.Data;

@Data
public class LogbackOutputDTO {
    private ILoggingEvent iLoggingEvent;
}
