package com.xm.starter.request.log.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.request.log.model.*;
import com.xm.starter.request.log.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fileTemplate")
public class RequestLogController {
    private @Autowired RequestLogService requestLogService;

    @RequestMapping("/page")
    public PageVO<RequestLogListVO> page(@RequestBody QueryRequestLogDTO query) {
        return requestLogService.page(query);
    }

    @RequestMapping("/list")
    public List<RequestLogListVO> list(@RequestBody QueryRequestLogDTO query) {
        return requestLogService.list(query);
    }

    @RequestMapping("/findById")
    public RequestLogDetailVO findById(Long id){
        return requestLogService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        requestLogService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestBody RequestLogInsertDTO insertDTO){
        return requestLogService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@RequestBody RequestLogUpdateByIdDTO updateByIdDTO){
        return requestLogService.updateById(updateByIdDTO);
    }
}
