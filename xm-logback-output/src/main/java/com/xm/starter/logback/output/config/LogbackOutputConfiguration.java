package com.xm.starter.logback.output.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.util.OptionHelper;
import com.xm.starter.base.exception.BaseException;
import com.xm.starter.logback.output.appender.LogbackOutputAppender;
import com.xm.starter.logback.output.model.LogbackOutputProperties;
import com.xm.starter.logback.output.service.LogbackOutputDingDingService;
import com.xm.starter.logback.output.service.LogbackOutputService;
import com.xm.starter.logback.output.service.LogbackOutputXxlJobService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.impl.StaticLoggerBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

@Slf4j
@Configuration
@EnableConfigurationProperties(LogbackOutputProperties.class)
@ConditionalOnProperty(prefix = "xm.starter.logback.output",name = "enable",havingValue = "true")
public class LogbackOutputConfiguration implements ApplicationRunner {
    private @Autowired LogbackOutputProperties logbackOutputProperties;


    @Bean
    @ConditionalOnProperty(prefix = "xm.starter.logback.output.ding-ding-config",name = "enable",havingValue = "true")
    public LogbackOutputService logbackOutputDingDingService(){
        return new LogbackOutputDingDingService(logbackOutputProperties);
    }


    @Bean
    @ConditionalOnProperty(prefix = "xm.starter.logback.output.xxl-job-config",name = "enable",havingValue = "true")
    public LogbackOutputService logbackOutputXxlJobService(){
        return new LogbackOutputXxlJobService(logbackOutputProperties);
    }


    @Override
    public void run(ApplicationArguments args){
        log.info("--- LogbackOutputAppender start ---");
        try {

            LoggerContext lc = (LoggerContext) StaticLoggerBinder.getSingleton().getLoggerFactory();

            PatternLayoutEncoder encoder = new PatternLayoutEncoder();
            encoder.setPattern(OptionHelper.substVars(logbackOutputProperties.getPattern(),lc));
            encoder.setCharset(Charset.forName(OptionHelper.substVars(logbackOutputProperties.getCharset(),lc)));
            encoder.setContext(lc);
            encoder.start();


            LogbackOutputAppender logbackOutputAppender = new LogbackOutputAppender(logbackOutputProperties);
            logbackOutputAppender.setContext(lc);
            logbackOutputAppender.setName(logbackOutputProperties.getAppenderName());
            logbackOutputAppender.setEncoder(encoder);
            logbackOutputAppender.start();

            Logger rootLogger = lc.getLogger(Logger.ROOT_LOGGER_NAME);
            rootLogger.addAppender(logbackOutputAppender);
            log.info("--- LogbackOutputAppender end ---");
        } catch (Exception e) {
            log.error("--- LogbackOutputAppender error, eMsg:{} ---", e);
        }
    }


}
