package com.xm.starter.task.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.task.mapper.TaskConfigMapper;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.dto.TaskConfigInsertDTO;
import com.xm.starter.task.model.dto.TaskConfigUpdateByIdDTO;
import com.xm.starter.task.model.po.TaskConfigPO;
import com.xm.starter.task.model.vo.TaskConfigDetailVO;
import com.xm.starter.task.model.vo.TaskConfigListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TaskConfigService {
    private @Autowired TaskConfigMapper taskConfigMapper;

    public String insert(TaskConfigInsertDTO insertDTO){
        TaskConfigPO taskConfigPO = new TaskConfigPO();
        BeanUtils.copyProperties(insertDTO,taskConfigPO);
        taskConfigMapper.insert(taskConfigPO);
        return taskConfigPO.getId().toString();
    }

    public String updateById(TaskConfigUpdateByIdDTO updateByIdDTO){
        TaskConfigPO taskConfigPO = new TaskConfigPO();
        BeanUtils.copyProperties(updateByIdDTO,taskConfigPO);
        taskConfigMapper.updateById(taskConfigPO);
        return taskConfigPO.getId().toString();
    }

    public List<TaskConfigListVO> list(QueryTaskDTO query) {
        return taskConfigMapper.list(query);
    }

    public PageVO<TaskConfigListVO> page(QueryTaskDTO query) {
        return taskConfigMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    public TaskConfigDetailVO findById(Long id) {
        TaskConfigDetailVO dictDetailVO = new TaskConfigDetailVO();
        BeanUtils.copyProperties(taskConfigMapper.selectById(id),dictDetailVO);
        return dictDetailVO;
    }
}
