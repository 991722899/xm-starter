package com.xm.starter.serial.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.serial.model.*;
import com.xm.starter.serial.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serial")
public class SerialController {
    private @Autowired SerialService serialService;
    @RequestMapping("/page")
    public PageVO<SerialListVO> page(@RequestBody QuerySerialDTO query) {
        return serialService.page(query);
    }

    @RequestMapping("/list")
    public List<SerialListVO> list(@RequestBody QuerySerialDTO query) {
        return serialService.list(query);
    }

    @RequestMapping("/findById")
    public SerialDetailVO findById(Long id){
        return serialService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        serialService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestBody SerialInsertDTO insertDTO){
        return serialService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@RequestBody SerialUpdateByIdDTO updateByIdDTO){
        return serialService.updateById(updateByIdDTO);
    }

}
