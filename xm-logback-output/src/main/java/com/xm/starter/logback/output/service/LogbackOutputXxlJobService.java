package com.xm.starter.logback.output.service;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.xm.starter.logback.output.appender.LogbackOutputAppender;
import com.xm.starter.logback.output.model.LogbackOutputDTO;
import com.xm.starter.logback.output.model.LogbackOutputProperties;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.log.XxlJobFileAppender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.impl.StaticLoggerBinder;

@Slf4j
public class LogbackOutputXxlJobService implements LogbackOutputService{
    private LogbackOutputProperties logbackOutputProperties;

    public LogbackOutputXxlJobService(LogbackOutputProperties logbackOutputProperties) {
        this.logbackOutputProperties = logbackOutputProperties;
    }

    @Override
    public void output(LogbackOutputDTO logbackOutputDTO) {
        if(XxlJobHelper.getJobId()>0){
            LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
            Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
            if(rootLogger.getAppender(logbackOutputProperties.getAppenderName())!=null){
                LogbackOutputAppender appender = (LogbackOutputAppender) rootLogger.getAppender(logbackOutputProperties.getAppenderName());
                logDetail(new String(appender.getEncoder().encode(logbackOutputDTO.getILoggingEvent())));
            }

        }
    }

    private static boolean logDetail(String appendLog) {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        if (xxlJobContext == null) {
            return false;
        }
        // appendlog
        String logFileName = xxlJobContext.getJobLogFileName();

        if (logFileName!=null && logFileName.trim().length()>0) {
            XxlJobFileAppender.appendLog(logFileName, appendLog);
            return true;
        } else {
            log.info(">>>>>>>>>>> {}", appendLog);
            return false;
        }
    }
}
