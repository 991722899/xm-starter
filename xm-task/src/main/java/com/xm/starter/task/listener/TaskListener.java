package com.xm.starter.task.listener;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.task.enums.TaskStatus;
import com.xm.starter.task.model.TaskEven;
import com.xm.starter.task.model.TaskProperties;
import com.xm.starter.task.model.dto.TaskDTO;
import com.xm.starter.task.service.Task;
import com.xm.starter.task.service.TaskService;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
@Component
public class TaskListener{
    private static final Logger log = LoggerFactory.getLogger(TaskListener.class);
    private @Autowired ObjectMapper objectMapper;
    private @Autowired(required = false) RocketMQTemplate rocketMQTemplate;
    private @Autowired TaskProperties taskProperties;

    protected void execute(List<MessageExt> msgs){
        for (MessageExt msg : msgs) {
            TaskDTO taskDTO = null;
            try {
                taskDTO = objectMapper.readValue(msg.getBody(), new TypeReference<TaskDTO<String>>() {});
                Task task = SpringUtil.getBean(taskDTO.getTaskConfig().getBeanName());
                JavaType javaType = objectMapper.getTypeFactory().constructType(ClassUtil.getTypeArgument(task.getClass(),0));
                taskDTO.setParams(objectMapper.readValue(ObjectUtil.defaultIfNull(taskDTO.getParams(),"{}").toString().getBytes(),javaType));
                start(taskDTO);
                TaskEven taskEven = task.execution(taskDTO);
                complete(taskDTO,taskEven);
            }catch (Exception e){
                try {
                    log.error(e.getMessage(),e);
                    TaskEven taskEven = new TaskEven();
                    taskEven.setStatus(TaskStatus.EXCEPTION.getCode());
                    taskEven.setCreateTime(LocalDateTime.now());
                    taskEven.setTaskId(taskDTO.getTask().getId());
                    taskEven.setExceptionMsg(ExceptionUtil.stacktraceToString(e,15000));
                    complete(taskDTO,taskEven);
                }catch (Exception exception){
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

    private void start(TaskDTO taskDTO) throws JsonProcessingException {
        TaskEven taskEven = new TaskEven();
        taskEven.setTaskId(taskDTO.getTask().getId());
        taskEven.setStatus(TaskStatus.IN_PROGRESS.getCode());
        taskEven.setCreateTime(LocalDateTime.now());
        String messageKey = StrUtil.join(",",taskDTO.getTaskConfig().getCode(),taskDTO.getTask().getId(), RandomUtil.randomNumbers(4));
        Message<String> message = MessageBuilder.withPayload(objectMapper.writeValueAsString(taskEven)).setHeader("KEYS", messageKey).build();
        rocketMQTemplate.syncSend(taskProperties.getUpdateStatusTopic(), message);
    }

    private void complete(TaskDTO taskDTO,TaskEven taskEven) throws JsonProcessingException {
        TaskEven even = new TaskEven();
        BeanUtils.copyProperties(taskEven,even);
        String messageKey = StrUtil.join(",",taskDTO.getTaskConfig().getCode(),taskDTO.getTask().getId(), RandomUtil.randomNumbers(4));
        Message<String> message = MessageBuilder.withPayload(objectMapper.writeValueAsString(even)).setHeader("KEYS", messageKey).build();
        rocketMQTemplate.syncSend(taskProperties.getUpdateStatusTopic(), message);
    }
}
