package com.xm.starter.task.listener;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.base.util.Assert;
import com.xm.starter.task.TaskNotFoundException;
import com.xm.starter.task.enums.TaskStatus;
import com.xm.starter.task.mapper.TaskMapper;
import com.xm.starter.task.model.TaskEven;
import com.xm.starter.task.model.po.TaskPO;
import com.xm.starter.task.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Slf4j
@Component
public class TaskUpdateStatusListener implements  MessageListenerOrderly {
    private @Autowired ObjectMapper objectMapper;
    private @Autowired TaskMapper taskMapper;
    private @Autowired TaskService taskService;

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        update(msgs);
        return ConsumeOrderlyStatus.SUCCESS;
    }

    private void update(List<MessageExt> list){
        for (MessageExt msg : list) {
            TaskPO taskPO = null;
            try {
                log.info("收到任务更新事件：{}",msg.getBody());
                TaskEven taskEven = objectMapper.readValue(msg.getBody(), TaskEven.class);
                taskPO = taskMapper.selectById(taskEven.getTaskId());
                Assert.isTrue(taskPO!=null,new TaskNotFoundException("任务不存在"));

                LambdaUpdateWrapper<TaskPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                lambdaUpdateWrapper.eq(TaskPO::getId,taskPO.getId());
                lambdaUpdateWrapper.set(TaskPO::getExceptionMsg,taskEven.getExceptionMsg());
                lambdaUpdateWrapper.set(TaskPO::getDownloadBatchNo,taskEven.getDownloadBatchNo());
                lambdaUpdateWrapper.set(TaskPO::getStatus,taskEven.getStatus());
                lambdaUpdateWrapper.set(taskEven.getStatus().equals(TaskStatus.IN_PROGRESS.getCode()),TaskPO::getBeginTime,taskEven.getCreateTime());
                lambdaUpdateWrapper.set(ArrayUtil.contains(new Integer[]{TaskStatus.SUCCESS.getCode(),
                        TaskStatus.EXCEPTION.getCode(),},taskEven.getStatus()),TaskPO::getEndTime,taskEven.getCreateTime());

                taskMapper.update(null,lambdaUpdateWrapper);
            }catch (TaskNotFoundException e){
                log.error("收到任务更新事件，更新异常",e);
            }catch (Exception e){
                log.error("收到任务更新事件，更新异常",e);
                if(taskPO!=null){
                    taskService.exception(taskPO.getId(),e);
                }
            }
        }
    }
}
