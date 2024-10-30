package com.xm.starter.logback.output.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.ThrowableProxy;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xm.starter.logback.output.appender.LogbackOutputAppender;
import com.xm.starter.logback.output.model.LogbackOutputDTO;
import com.xm.starter.logback.output.model.LogbackOutputProperties;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

public class LogbackOutputDingDingService implements LogbackOutputService{
    private LogbackOutputProperties logbackOutputProperties;
    /**
     * 剩余次数
     */
    private volatile AtomicLong remaining = null;
    /**
     * 上次重置时间
     */
    private volatile Long minute = null;


    public LogbackOutputDingDingService(LogbackOutputProperties logbackOutputProperties) {
        this.logbackOutputProperties = logbackOutputProperties;
        this.remaining = new AtomicLong(logbackOutputProperties.getDingDingConfig().getPerMinute());
        this.minute = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
    }

    @Override
    public void output(LogbackOutputDTO logbackOutputDTO) {
        ILoggingEvent eventObject = logbackOutputDTO.getILoggingEvent();
        if(eventObject.getLevel()!=null && eventObject.getLevel().toInt()== Level.ERROR_INT){
            LogbackOutputProperties.DingDingConfig dingDingConfig = logbackOutputProperties.getDingDingConfig();

            Throwable throwable = ((ThrowableProxy)eventObject.getThrowableProxy()).getThrowable();
            if(CollUtil.isEmpty(dingDingConfig.getExcludeExceptions()) ||
                    !dingDingConfig.getExcludeExceptions().contains(throwable.getClass().getSimpleName())) {

                Long nowMinute = Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
                if(minute<nowMinute){
                    minute = nowMinute;
                    this.remaining.set(dingDingConfig.getPerMinute());
                }
                if(this.remaining.get()<=0){
                    return;
                }

                String requestUrl = "";
                if(RequestContextHolder.getRequestAttributes()!=null){
                    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                    requestUrl  = StrUtil.format("请求地址：{}\r\n",attributes.getRequest().getRequestURL().toString());
                }


                LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();
                Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
                if(rootLogger.getAppender(logbackOutputProperties.getAppenderName())!=null){
                    LogbackOutputAppender appender = (LogbackOutputAppender) rootLogger.getAppender(logbackOutputProperties.getAppenderName());
                    String msg = "liyi99-"+requestUrl + new String(appender.getEncoder().encode(eventObject));
                    HttpRequest request = HttpUtil.createPost(dingDingConfig.getDingTalkReboot());
                    ObjectNode node = new ObjectMapper().createObjectNode();
                    node.put("msgtype", "text");
                    ObjectNode text = new ObjectMapper().createObjectNode();
                    text.put("content",msg.length()>dingDingConfig.getContextMaxLength()?msg.substring(0,dingDingConfig.getContextMaxLength()):msg);
                    node.put("text", text);
                    request.body(node.toPrettyString());
                    request.execute().body();
                    this.remaining.decrementAndGet();
                }

            }
        }
    }
}
