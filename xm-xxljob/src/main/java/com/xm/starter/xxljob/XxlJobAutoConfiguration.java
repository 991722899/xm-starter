package com.xm.starter.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @Description: xxljob
 * @author: chencc
 * @date:  2022/2/23 10:21
 */
@Configuration
@ConditionalOnProperty(prefix = XxlJobProperties.PREFIX,name = "enable",havingValue = "true")
@EnableConfigurationProperties({XxlJobProperties.class})
public class XxlJobAutoConfiguration {

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties){
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddress());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppName());
        xxlJobSpringExecutor.setAddress(xxlJobProperties.getExecutor().getAddress());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());

        return xxlJobSpringExecutor;
    }
}
