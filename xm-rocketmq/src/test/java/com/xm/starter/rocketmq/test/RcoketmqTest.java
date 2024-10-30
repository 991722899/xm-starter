package com.xm.starter.rocketmq.test;

import cn.hutool.core.util.StrUtil;
import com.xm.starter.rocketmq.config.RocketmqConfiguration;
import com.xm.starter.rocketmq.model.RocketmqProperties;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootTest(classes = {RocketmqConfiguration.class, TestConsume.class})
@RunWith(SpringRunner.class)
public class RcoketmqTest {
    private @Autowired RocketMQTemplate rocketMQTemplate;
    private @Autowired RocketmqProperties rocketmqProperties;

    @Test
    public void  test(){
        Message<String> message = MessageBuilder.withPayload(new String("776")).setHeader("KEYS", StrUtil.join("-")).build();
        rocketMQTemplate.syncSend(rocketmqProperties.getConsumeConfig().get("test1").getTopic(),message);
    }
}
