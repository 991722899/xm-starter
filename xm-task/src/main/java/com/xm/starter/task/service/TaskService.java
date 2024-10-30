package com.xm.starter.task.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.rocketmq.model.RocketmqProperties;
import com.xm.starter.task.enums.RunTypeEnum;
import com.xm.starter.task.enums.TaskStatus;
import com.xm.starter.task.mapper.TaskConfigMapper;
import com.xm.starter.task.mapper.TaskMapper;
import com.xm.starter.task.model.*;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.dto.TaskDTO;
import com.xm.starter.task.model.dto.TaskInsertDTO;
import com.xm.starter.task.model.po.TaskConfigPO;
import com.xm.starter.task.model.po.TaskPO;
import com.xm.starter.task.model.vo.TaskDetailVO;
import com.xm.starter.task.model.vo.TaskListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class TaskService {
    private @Autowired ObjectMapper objectMapper;
    private @Autowired TaskMapper taskMapper;
    private @Autowired TaskConfigMapper taskConfigMapper;
    private @Autowired TaskConfigService taskConfigService;
    private @Autowired TaskService taskService;
    private @Autowired(required = false) RocketMQTemplate rocketMQTemplate;
    private @Autowired(required = false) RocketmqProperties rocketmqProperties;

    public void start(Long id){
        taskMapper.update(new LambdaUpdateWrapper<TaskPO>().eq(TaskPO::getId,id)
                .set(TaskPO::getStatus,TaskStatus.IN_PROGRESS.getCode())
                .set(TaskPO::getBeginTime,LocalDateTime.now()));
    }
    public void success(Long id, TaskEven taskExecutionResult){
        LambdaUpdateWrapper<TaskPO> taskPOLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        taskPOLambdaUpdateWrapper.eq(TaskPO::getId,id);
        taskPOLambdaUpdateWrapper.set(TaskPO::getStatus,TaskStatus.SUCCESS.getCode());
        taskPOLambdaUpdateWrapper.set(TaskPO::getEndTime,LocalDateTime.now());
        if(taskExecutionResult!=null){
            taskPOLambdaUpdateWrapper.set(TaskPO::getDownloadUrl,taskExecutionResult.getDownloadUrl());
            taskPOLambdaUpdateWrapper.set(TaskPO::getDownloadBatchNo,taskExecutionResult.getDownloadBatchNo());
        }
        taskMapper.update(taskPOLambdaUpdateWrapper);
    }

    public void exception(Long id,Throwable e){
        taskMapper.update(new LambdaUpdateWrapper<TaskPO>().eq(TaskPO::getId,id)
                .set(TaskPO::getStatus,TaskStatus.EXCEPTION.getCode())
                .set(TaskPO::getEndTime,LocalDateTime.now())
                .set(TaskPO::getExceptionMsg, ExceptionUtil.stacktraceToString(e,15000)));
    }

    private TaskEven sync(TaskConfigPO taskConfigPO, TaskDTO<String> taskDTO) throws JsonProcessingException {
        try {
            Task taskBean = SpringUtil.getBean(taskConfigPO.getBeanName());
            taskDTO.setParams(objectMapper.readValue(taskDTO.getParams(),objectMapper.getTypeFactory().constructType(ClassUtil.getTypeArgument(taskBean.getClass(),0))));
            start(taskDTO.getTask().getId());
            TaskEven taskEven = taskBean.execution(taskDTO);
            success(taskDTO.getTask().getId(),taskEven);
            return taskEven;
        }catch (Exception e){
            exception(taskDTO.getTask().getId(),e);
            throw e;
        }
    }

    private TaskEven async(TaskConfigPO taskConfigPO, TaskDTO taskDTO){
        try {
            RocketmqProperties.ConsumeConfig config = rocketmqProperties.getConsumeConfig().get(taskConfigPO.getGroupCode());
            Assert.isTrue(config!=null,StrUtil.format("code:{}对应的消费者配置不存在",taskConfigPO.getGroupCode()));
            String messageKey = StrUtil.join(",",taskConfigPO.getCode(),taskDTO.getTask().getId(), RandomUtil.randomNumbers(4));
            Message<String> message = MessageBuilder.withPayload(objectMapper.writeValueAsString(taskDTO)).setHeader("KEYS", messageKey).build();
            rocketMQTemplate.syncSend(config.getTopic(), message);
        }catch (Exception e){
            exception(taskDTO.getTask().getId(),e);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public TaskEven insert(TaskInsertDTO insertDTO) throws JsonProcessingException {
        TaskEven taskExecutionResult = null;
        TaskConfigPO taskConfigPO = taskConfigMapper.selectOne(new LambdaUpdateWrapper<TaskConfigPO>().eq(TaskConfigPO::getCode,insertDTO.getCode()));
        Assert.isTrue(taskConfigPO!=null,"任务配置不存在");


        TaskPO task = new TaskPO();
        task.setBeginTime(LocalDateTime.now());
        task.setConfigId(taskConfigPO.getId());
        task.setStatus(TaskStatus.PENDING.getCode());
        task.setName(taskConfigPO.getName()+LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN)));
        taskMapper.insert(task);

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setParams(insertDTO.getParams());
        taskDTO.setTaskConfig(taskConfigService.findById(taskConfigPO.getId()));
        taskDTO.setTask(taskService.findById(task.getId()));


        if(taskConfigPO.getRunType().equals(RunTypeEnum.SYNCHRONOUS.getCode())){
            taskExecutionResult = sync(taskConfigPO,taskDTO);
        }else{
            taskExecutionResult = async(taskConfigPO,taskDTO);
        }

        return taskExecutionResult;
    }


    public List<TaskListVO> list(QueryTaskDTO query) {
        return taskMapper.list(query);
    }

    public PageVO<TaskListVO> page(QueryTaskDTO query) {
        return taskMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    public TaskDetailVO findById(Long id) {
        TaskDetailVO dictDetailVO = new TaskDetailVO();
        BeanUtils.copyProperties(taskMapper.selectById(id),dictDetailVO);
        return dictDetailVO;
    }
}
