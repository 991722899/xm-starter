package com.xm.starter.business.log.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.business.log.model.*;
import com.xm.starter.business.log.service.BusinessLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "sysBusinessLog", description = "业务日志")
@RestController
@RequestMapping("/sysBusinessLog")
public class BusinessLogController {
    private @Autowired BusinessLogService businessLogService;

    @RequestMapping("/list")
    public List<BusinessLogListVO> list(@RequestBody QueryBusinessLogDTO query){
        return businessLogService.list(query);
    }
    @RequestMapping("/detailByLogId")
    public BusinessLogDetailVO detailByLogId(Long id){
        return businessLogService.detailByLogId(id);
    }

    @RequestMapping("/findById")
    public BusinessLogDetailVO findById(Long id){
        return businessLogService.findById(id);
    }

    @PostMapping("/page")
    public PageVO<BusinessLogListVO> page(@RequestBody QueryBusinessLogDTO query){
        return businessLogService.page(query);
    }

    @PostMapping("/insert")
    public String insert(@Valid @RequestBody BusinessLogInsertDto insertDTO){
        return businessLogService.insert(insertDTO);
    }

    @PostMapping("/updateById")
    public String updateById(@Valid @RequestBody BusinessLogUpdateDTO updateDTO){
        return businessLogService.updateById(updateDTO);
    }
}
