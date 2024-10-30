package com.xm.starter.task.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.dto.TaskInsertDTO;
import com.xm.starter.task.model.vo.TaskDetailVO;
import com.xm.starter.task.model.vo.TaskListVO;
import com.xm.starter.task.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "task", description = "任务管理")
@RestController
@RequestMapping("/task")
public class TaskController {
    private @Autowired TaskService taskService;


    @PostMapping("/list")
    public List<TaskListVO> list(@RequestBody QueryTaskDTO query){
        return taskService.list(query);
    }

    @GetMapping("/findById")
    public TaskDetailVO findById(Long id){
        return taskService.findById(id);
    }

    @PostMapping("/page")
    public PageVO<TaskListVO> page(@RequestBody QueryTaskDTO query){
        return taskService.page(query);
    }

    @PostMapping("/insert/{code}")
    public void insert(@PathVariable String code,@Valid @RequestBody TaskInsertDTO taskInsertDTO) throws JsonProcessingException {
        taskInsertDTO.setCode(code);
        taskService.insert(taskInsertDTO);
    }
}
