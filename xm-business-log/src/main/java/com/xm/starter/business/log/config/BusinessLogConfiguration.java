package com.xm.starter.business.log.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.xm.starter.business.log.model.BusinessLog;
import com.xm.starter.business.log.model.BusinessLogProperties;
import com.xm.starter.business.log.model.BusinessLogs;
import com.xm.starter.business.log.service.BusinessLogHandler;
import com.xm.starter.business.log.util.JavascriptUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Aspect
@Slf4j
@Configuration
@ComponentScan(value = "com.xm.starter.business.log")
@MapperScan(value = "com.xm.starter.business.log.mapper")
@ConditionalOnProperty(prefix = "xm.starter.business.log",name = "enable",havingValue = "true")
@EnableConfigurationProperties(BusinessLogProperties.class)
public class BusinessLogConfiguration {
    private @Resource(name = "businessLogObjectMapper") ObjectMapper objectMapper;

    private Object handle(ProceedingJoinPoint proceedingJoinPoint, BusinessLog... businessLog) throws Throwable {
        List<LogInfo> logInfoList = Arrays.stream(businessLog).map(LogInfo::new).collect(Collectors.toList());
        try {
            for (LogInfo logInfo : logInfoList) {
                BusinessLog log  = logInfo.getBusinessLog();
                BusinessLogHandler businessLogHandler = SpringUtil.getBean(log.value());
                String scriptValue = StrUtil.isNotBlank(log.paramsScript())?JavascriptUtil.eval(log.paramsScript(),proceedingJoinPoint.getArgs()):"";
                Class<?> c = ClassUtil.getTypeArgument(businessLogHandler.getClass(),0);
                JavaType javaType = objectMapper.getTypeFactory().constructType(c);
                Object params = objectMapper.readValue(ObjectUtil.defaultIfBlank(scriptValue, Collection.class.isAssignableFrom(c)?"[]":"{}"),javaType);
                logInfo.setBefore(businessLogHandler.before(params));
                logInfo.setBusinessLogHandler(businessLogHandler);
            }
        }catch (Exception e){
            log.error("业务日志处理异常",e);
        }
        Object result = proceedingJoinPoint.proceed();
        try {
            for (LogInfo logInfo : logInfoList) {
                BusinessLog log  = logInfo.getBusinessLog();
                BusinessLogHandler businessLogHandler = SpringUtil.getBean(log.value());
                Class<?> c = ClassUtil.getTypeArgument(businessLogHandler.getClass(),0);
                JavaType javaType = objectMapper.getTypeFactory().constructType(c);
                String scriptValue = StrUtil.isNotBlank(log.returnScript())?JavascriptUtil.eval(log.returnScript(),result):JavascriptUtil.eval(log.paramsScript(),objectMapper,proceedingJoinPoint.getArgs());
                Object params = objectMapper.readValue(ObjectUtil.defaultIfBlank(scriptValue, Collection.class.isAssignableFrom(c)?"[]":"{}"),javaType);
                logInfo.setAfter(businessLogHandler.after(params));
                //输出日志
                businessLogHandler.outPut(logInfo.getBefore(),logInfo.getAfter(),log);
            }
        }catch (Exception e){
            log.error("业务日志处理异常",e);
        }
        return result;
    }

    @Around(value = "@annotation(businessLogs)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, BusinessLogs businessLogs) throws Throwable {
        return handle(proceedingJoinPoint, businessLogs.logs());
    }

    @Around(value = "@annotation(businessLog)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint, BusinessLog businessLog) throws Throwable {
        return handle(proceedingJoinPoint, businessLog);
    }

    @Bean(value = "businessLogObjectMapper")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new DateDeserializerSerializer());

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        objectMapper.registerModule(javaTimeModule);

        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }

    public static class DateDeserializerSerializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext text) throws IOException {
            String dateStr = jsonParser.getText();
            if (StrUtil.isBlank(dateStr)) {
                return null;
            }
            try {
                return LocalDate.parse(dateStr, DatePattern.NORM_DATE_FORMATTER);
            } catch (Exception e) {
                return LocalDateTime.parse(dateStr,DatePattern.NORM_DATETIME_FORMATTER).toLocalDate();
            }
        }
    }

    @Data
    private static class LogInfo{
        private BusinessLog businessLog;
        private List<Object> before;
        private List<Object> after;
        private BusinessLogHandler businessLogHandler;

        public LogInfo(BusinessLog businessLog) {
            this.businessLog = businessLog;
        }
    }
}
