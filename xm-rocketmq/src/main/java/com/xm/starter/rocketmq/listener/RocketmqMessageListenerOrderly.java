package com.xm.starter.rocketmq.listener;

import com.xm.starter.rocketmq.model.RocketmqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class RocketmqMessageListenerOrderly extends RocketmqMessageListener implements MessageListenerOrderly {


    public RocketmqMessageListenerOrderly(RocketmqProperties properties, RocketmqProperties.ConsumeConfig consumeConfig) {
        super(properties, consumeConfig);
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        for (MessageExt messageExt : list) {
            try {
                 handler(messageExt);
            } catch (Exception e) {
                log.error("rocketmq 消费异常",e);
                return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
