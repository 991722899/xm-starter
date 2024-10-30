package com.xm.starter.rocketmq.listener;

import com.xm.starter.rocketmq.model.RocketmqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
@Slf4j
public class RocketmqMessageListenerConcurrently extends RocketmqMessageListener implements MessageListenerConcurrently {

    public RocketmqMessageListenerConcurrently(RocketmqProperties properties, RocketmqProperties.ConsumeConfig consumeConfig) {
        super(properties, consumeConfig);
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list) {
            try {
                handler(messageExt);
            } catch (Exception e) {
                log.error("rocketmq 消费异常",e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
