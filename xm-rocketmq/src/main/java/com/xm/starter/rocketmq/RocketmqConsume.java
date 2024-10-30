package com.xm.starter.rocketmq;

import com.xm.starter.rocketmq.model.RocketmqMessage;

/**
* @description：消息者接口
* @author：陈超超
* @time：2024/6/24 11:38
*/
public interface RocketmqConsume {
    void handler(RocketmqMessage message);
    void before(RocketmqMessage message);
    void after(RocketmqMessage message);
    void exception(Throwable e, RocketmqMessage message);
}
