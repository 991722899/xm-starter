package com.xm.starter.rocketmq.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.xm.starter.rocketmq.listener.RocketmqMessageListenerConcurrently;
import com.xm.starter.rocketmq.listener.RocketmqMessageListenerOrderly;
import com.xm.starter.rocketmq.model.RocketmqProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.consumer.rebalance.AllocateMessageQueueAveragely;
import org.apache.rocketmq.remoting.RPCHook;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Slf4j
@Configuration
@AutoConfigureAfter(RocketMQAutoConfiguration.class)
@ConditionalOnProperty(prefix = "xm.starter.rocketmq",name = "enable",havingValue = "true")
@EnableConfigurationProperties({RocketmqProperties.class})
public class RocketmqConfiguration implements SmartInitializingSingleton, ApplicationContextAware{
    private @Autowired  RocketmqProperties properties;
    private ApplicationContext applicationContext;


    @Override
    public void afterSingletonsInstantiated() {
        if(CollUtil.isNotEmpty(properties.getConsumeConfig())){
            for (String s : properties.getConsumeConfig().keySet()) {
                try {
                    log.info("开始初始化消费者：{}",s);
                    RocketmqProperties.ConsumeConfig config = properties.getConsumeConfig().get(s);
                    Assert.notNull(config.getTopic(), StrUtil.format("{},Property 'topic' is required",s));
                    Assert.notNull(config.getNameServer(), StrUtil.format("{},Property 'nameServer' is required",s));
                    Assert.notNull(config.getGroup(), StrUtil.format("{},Property 'group' is required",s));
                    Assert.notNull(config.getBeanName(), StrUtil.format("{},Property 'beanName' is required",s));


                    DefaultMQPushConsumer consumer = null;
                    RPCHook rpcHook = RocketMQUtil.getRPCHookByAkSk(applicationContext.getEnvironment(),config.getAccessKey(), config.getSecretKey());
                    if (Objects.nonNull(rpcHook)) {
                        consumer = new DefaultMQPushConsumer(config.getGroup(), rpcHook, new AllocateMessageQueueAveragely(),
                                config.getEnableMsgTrace(), this.applicationContext.getEnvironment().
                                resolveRequiredPlaceholders(config.getCustomizedTraceTopic()));
                        consumer.setVipChannelEnabled(false);
                    } else {
                        consumer = new DefaultMQPushConsumer(config.getGroup(), config.getEnableMsgTrace(),
                                this.applicationContext.getEnvironment().
                                        resolveRequiredPlaceholders(config.getCustomizedTraceTopic()));
                    }
                    consumer.setNamespace(config.getNamespace());
                    consumer.setInstanceName(RocketMQUtil.getInstanceName(config.getNameServer()));

                    String customizedNameServer = this.applicationContext.getEnvironment().resolveRequiredPlaceholders(config.getNameServer());
                    if (customizedNameServer != null) {
                        consumer.setNamesrvAddr(customizedNameServer);
                    } else {
                        consumer.setNamesrvAddr(config.getNameServer());
                    }
                    String accessChannel = applicationContext.getEnvironment().resolvePlaceholders(config.getAccessChannel());
                    if (!StringUtils.isEmpty(accessChannel)) {
                        AccessChannel accessChannel1 = AccessChannel.valueOf(accessChannel);
                        consumer.setAccessChannel(accessChannel1);
                    }

                    //set the consumer core thread number and maximum thread number has the same value
                    consumer.setConsumeThreadMax(config.getThreadMax());
                    consumer.setConsumeThreadMin(config.getThreadNumber());
                    consumer.setConsumeTimeout(config.getTimeout());
                    consumer.setMaxReconsumeTimes(config.getMaxReconsumeTimes());
                    consumer.setAwaitTerminationMillisWhenShutdown(config.getAwaitTerminationMillisWhenShutdown());
                    switch (config.getMessageModel()) {
                        case BROADCASTING:
                            consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.BROADCASTING);
                            break;
                        case CLUSTERING:
                            consumer.setMessageModel(org.apache.rocketmq.common.protocol.heartbeat.MessageModel.CLUSTERING);
                            break;
                        default:
                            throw new IllegalArgumentException("Property 'messageModel' was wrong.");
                    }

                    switch (config.getSelectorType()) {
                        case TAG:
                            consumer.subscribe(config.getTopic(), config.getSelectorExpression());
                            break;
                        case SQL92:
                            consumer.subscribe(config.getTopic(), MessageSelector.bySql(config.getSelectorExpression()));
                            break;
                        default:
                            throw new IllegalArgumentException("Property 'selectorType' was wrong.");
                    }

                    switch (config.getConsumeMode()) {
                        case ORDERLY:
                            //优先使用匹配listener，没有就走默认listener(内部使用RocketmqConsume接入)
                            try{
                                consumer.setMessageListener(SpringUtil.getBean(config.getBeanName()));
                            }catch (Exception e){
                                consumer.setMessageListener(new RocketmqMessageListenerOrderly(properties,config));
                            }
                            break;
                        case CONCURRENTLY:
                            try {
                                consumer.setMessageListener(SpringUtil.getBean(config.getBeanName()));
                            }catch (Exception e){
                                consumer.setMessageListener(new RocketmqMessageListenerConcurrently(properties,config));
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Property 'consumeMode' was wrong.");
                    }
                    consumer.setUseTLS(config.getTlsEnable());
                    consumer.start();
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
                log.info("消费者初始化完成：{}",s);
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
