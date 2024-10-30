package com.xm.starter.task.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.dto.TaskConfigInsertDTO;
import com.xm.starter.task.model.dto.TaskConfigUpdateByIdDTO;
import com.xm.starter.task.model.vo.TaskConfigDetailVO;
import com.xm.starter.task.model.vo.TaskConfigListVO;
import com.xm.starter.task.service.TaskConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "taskConfig", description = "任务配置管理")
@RestController
@RequestMapping("/taskConfig")
public class TaskConfigController {
    private @Autowired TaskConfigService taskConfigService;


    @Operation(summary = "任务配置列表查询")
    @PostMapping("/list")
    public List<TaskConfigListVO> list(@RequestBody QueryTaskDTO query){
        return taskConfigService.list(query);
    }

    @Operation(summary = "任务配置主键查询")
    @GetMapping("/findById")
    public TaskConfigDetailVO findById(Long id){
        return taskConfigService.findById(id);
    }

    @Operation(summary = "任务配置分页查询")
    @PostMapping("/page")
    public PageVO<TaskConfigListVO> page(@RequestBody QueryTaskDTO query){
        return taskConfigService.page(query);
    }

    @Operation(summary = "添加任务配置")
    @PostMapping("/insert")
    public String insert(@Valid @RequestBody TaskConfigInsertDTO insertDTO){
        return taskConfigService.insert(insertDTO);
    }

    @Operation(summary = "任务配置更新（根据主键）")
    @PostMapping("/updateById")
    public String updateById(@Valid @RequestBody TaskConfigUpdateByIdDTO updateDTO){
        return taskConfigService.updateById(updateDTO);
    }

}
