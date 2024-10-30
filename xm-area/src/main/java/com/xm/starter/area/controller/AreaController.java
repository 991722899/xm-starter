package com.xm.starter.area.controller;

import com.xm.starter.area.model.*;
import com.xm.starter.area.service.AreaService;
import com.xm.starter.base.model.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/area")
public class AreaController {
    private @Autowired AreaService serialService;
    @RequestMapping("/page")
    public PageVO<AreaListVO> page(@RequestBody QueryArealDTO query) {
        return serialService.page(query);
    }

    @RequestMapping("/list")
    public List<AreaListVO> list(@RequestBody QueryArealDTO query) {
        return serialService.list(query);
    }

    @RequestMapping("/findById")
    public AreaDetailVO findById(Long id){
        return serialService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        serialService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestBody AreaInsertDTO insertDTO){
        return serialService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@RequestBody AreaUpdateByIdDTO updateByIdDTO){
        return serialService.updateById(updateByIdDTO);
    }

}
