package com.xm.starter.rocketmq.test;

import com.xm.starter.rocketmq.RocketmqConsume;
import com.xm.starter.rocketmq.model.RocketmqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestConsume implements RocketmqConsume {
    @Override
    public void handler(RocketmqMessage message) {
        message.getMessageExt();
        log.info("");
    }

    @Override
    public void before(RocketmqMessage message) {

    }

    @Override
    public void after(RocketmqMessage message) {

    }

    @Override
    public void exception(Throwable e, RocketmqMessage message) {

    }
}
