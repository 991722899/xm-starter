package com.xm.starter.sms.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.sms.mapper.SMSMapper;
import com.xm.starter.sms.model.SMSProperties;
import com.xm.starter.sms.service.SMSOSSServiceImpl;
import com.xm.starter.sms.service.SMSService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(value = "com.xm.starter.sms")
@MapperScan(value = "com.xm.starter.sms.mapper")
@ConditionalOnProperty(prefix = "xm.starter.sms",name = "enable",havingValue = "true")
@EnableConfigurationProperties(SMSProperties.class)
public class SMSConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "xm.starter.sms.aliYun",name = "enable",havingValue = "true")
    public Client aliyunSMSClient(SMSProperties smsProperties) throws Exception {
        SMSProperties.AliYun aliYun = smsProperties.getAliYun();
        Config config = new Config();
        config.setAccessKeyId(aliYun.getAccessKeyId());
        config.setAccessKeySecret(aliYun.getAccessKeySecret());
        config.setEndpoint(aliYun.getEndPoint());
        return new Client(config);
    }


    @Primary
    @Bean
    @ConditionalOnProperty(prefix = "xm.starter.sms.aliYun",name = "enable",havingValue = "true")
    public SMSService aliyunSMSService(SMSMapper smsMapper, Client client, ObjectMapper objectMapper) {
        return new SMSOSSServiceImpl(smsMapper,client,objectMapper);
    }
}
