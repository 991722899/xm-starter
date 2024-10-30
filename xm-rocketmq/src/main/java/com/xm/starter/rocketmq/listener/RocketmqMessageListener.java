package com.xm.starter.rocketmq.listener;

import cn.hutool.extra.spring.SpringUtil;
import com.xm.starter.rocketmq.RocketmqConsume;
import com.xm.starter.rocketmq.model.RocketmqMessage;
import com.xm.starter.rocketmq.model.RocketmqProperties;
import org.apache.rocketmq.common.message.MessageExt;
/**
* @description：监听器
* @author：陈超超
* @time：2024/6/24 11:38
*/
public class RocketmqMessageListener {
    protected RocketmqProperties properties;
    protected RocketmqProperties.ConsumeConfig consumeConfig;

    public RocketmqMessageListener(RocketmqProperties properties, RocketmqProperties.ConsumeConfig consumeConfig) {
        this.properties = properties;
        this.consumeConfig = consumeConfig;
    }

    public void handler(MessageExt message){
        RocketmqConsume consume = null;
        RocketmqMessage mqMessage = new RocketmqMessage();
        try{
            consume = SpringUtil.getBean(consumeConfig.getBeanName());
            consume.before(mqMessage);
            mqMessage.setMessageExt(message);
            consume.handler(mqMessage);
            consume.after(mqMessage);
        }catch (Exception e){
            if(consume!=null){
                consume.exception(e,mqMessage);
            }
            throw e;
        }
    }
}
