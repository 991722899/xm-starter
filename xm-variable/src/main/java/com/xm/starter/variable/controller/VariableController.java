package com.xm.starter.variable.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.variable.model.*;
import com.xm.starter.variable.service.VariableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/variable")
public class VariableController {
    private @Autowired VariableService variableService;
    @RequestMapping("/page")
    public PageVO<VariableListVO> page(@RequestBody QueryVariableDTO query) {
        return variableService.page(query);
    }

    @RequestMapping("/list")
    public List<VariableListVO> list(@RequestBody QueryVariableDTO query) {
        return variableService.list(query);
    }

    @RequestMapping("/findById")
    public VariableDetailVO findById(Long id){
        return variableService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        variableService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestBody VariableInsertDTO insertDTO){
        return variableService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@RequestBody VariableUpdateByIdDTO updateByIdDTO){
        return variableService.updateById(updateByIdDTO);
    }

}
