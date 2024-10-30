package com.xm.starter.dict.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.dict.model.*;
import com.xm.starter.dict.service.DictService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "dict", description = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {
    private @Autowired DictService dictService;

    @RequestMapping("/list")
    public List<DictListVO> list(@RequestBody QueryDictDTO query){
        return dictService.list(query);
    }
    @RequestMapping("/listTree")
    public List<DictListVO> listTree(@RequestBody QueryDictDTO query){
        return dictService.listTree(query);
    }

    @RequestMapping("/findById")
    public DictDetailVO findById(Long id){
        return dictService.findById(id);
    }

    @PostMapping("/page")
    public PageVO<DictListVO> page(@RequestBody QueryDictDTO query){
        return dictService.page(query);
    }

    @PostMapping("/insert")
    public String insert(@Valid @RequestBody DictInsertDTO insertDTO){
        return dictService.insert(insertDTO);
    }

    @PostMapping("/updateById")
    public String updateById(@Valid @RequestBody DictUpdateDTO updateDTO){
        return dictService.updateById(updateDTO);
    }
}
