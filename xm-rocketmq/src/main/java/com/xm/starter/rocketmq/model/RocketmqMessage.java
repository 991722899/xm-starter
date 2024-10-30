package com.xm.starter.rocketmq.model;

import lombok.Data;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.Serializable;

@Data
public class RocketmqMessage implements Serializable {
    private MessageExt messageExt;
}
