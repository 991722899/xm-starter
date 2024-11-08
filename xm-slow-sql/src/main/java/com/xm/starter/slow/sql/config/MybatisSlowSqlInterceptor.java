package com.xm.starter.slow.sql.config;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xm.starter.slow.sql.mapper.SlowSqlMapper;
import com.xm.starter.slow.sql.model.SlowSqlPO;
import com.xm.starter.slow.sql.model.SlowSqlProperties;
import com.xm.starter.slow.sql.service.SlowSqlOutput;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Slf4j
@Intercepts({
        @Signature(type= Executor.class,method="query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
@Component
public class MybatisSlowSqlInterceptor implements Interceptor {
    private @Autowired SlowSqlProperties slowSqlProperties;
    private @Autowired SlowSqlMapper slowSqlMapper;



    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        MappedStatement statement = (MappedStatement) invocation.getArgs()[0];
        if(slowSqlProperties.getExcludeMapperId()!=null && slowSqlProperties.getExcludeMapperId().stream().anyMatch(j->statement.getId().startsWith(j))){
            return invocation.proceed();
        }
        BoundSql boundSql = statement.getBoundSql(invocation.getArgs()[1]);
        Field field = statement.getClass().getDeclaredField("configuration");
        field.setAccessible(true);
        Configuration configuration = (Configuration) ReflectionUtils.getField(field, statement);
        // 这种姿势，与mybatis源码中参数解析姿势一致
        MetaObject metaObject = configuration.newMetaObject(boundSql.getParameterObject());
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");  // sql语句中多个空格都用一个空格代替
        for (ParameterMapping parameterMapping : boundSql.getParameterMappings()) {
            String propertyName = parameterMapping.getProperty();
            if (metaObject.hasGetter(propertyName)) {
                Object obj = metaObject.getValue(propertyName);
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
            } else if (boundSql.hasAdditionalParameter(propertyName)) {
                Object obj = boundSql.getAdditionalParameter(propertyName);  // 该分支是动态sql
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
            } else {
                sql = sql.replaceFirst("\\?", "缺失");
            }
        }
        Object object = invocation.proceed();
        long timeConsuming = System.currentTimeMillis()-start;
        if(timeConsuming>=slowSqlProperties.getSlowSqlTime()){
            try {
                String stackInfo = Arrays.stream(Thread.currentThread().getStackTrace())
                        .map(StackTraceElement::toString)
                        .collect(Collectors.joining("\n"));
                log.info("慢sql:{},stackInfo:{}",sql,stackInfo);
                if(slowSqlMapper.selectCount(new LambdaQueryWrapper<SlowSqlPO>().eq(SlowSqlPO::getMapperId,statement.getId()))==0){
                    SlowSqlPO slowSqlPO = new SlowSqlPO();
                    slowSqlPO.setSqlText(sql);
                    slowSqlPO.setStackInfo(stackInfo);
                    slowSqlPO.setTimeConsuming(timeConsuming);
                    slowSqlPO.setMapperId(statement.getId());
                    slowSqlMapper.insert(slowSqlPO);
                    String [] names = SpringUtil.getBeanNamesForType(SlowSqlOutput.class);
                    for (String name : names) {
                        SlowSqlOutput slowSqlOutput = SpringUtil.getBean(name);
                        slowSqlOutput.output(slowSqlPO);
                    }
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }
        }
        return  object;
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

}
