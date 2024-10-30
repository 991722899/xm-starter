package com.xm.starter.file.template.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.file.template.model.*;
import com.xm.starter.file.template.service.FileTemplateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "fileTemplate", description = "文件模板")
@RestController
@RequestMapping("/fileTemplate")
public class FileTemplateController {
    private @Autowired FileTemplateService fileTemplateService;

    @RequestMapping("/page")
    public PageVO<FileTemplateListVO> page(@RequestBody QueryFileTemplateDTO query) {
        return fileTemplateService.page(query);
    }

    @RequestMapping("/list")
    public List<FileTemplateListVO> list(@RequestBody QueryFileTemplateDTO query) {
        return fileTemplateService.list(query);
    }

    @RequestMapping("/findById")
    public FileTemplateDetailVO findById(Long id){
        return fileTemplateService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        fileTemplateService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@RequestBody FileTemplateInsertDTO insertDTO){
        return fileTemplateService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@RequestBody FileTemplateUpdateByIdDTO updateByIdDTO){
        return fileTemplateService.updateById(updateByIdDTO);
    }
}
