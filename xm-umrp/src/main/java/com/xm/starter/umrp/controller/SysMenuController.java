package com.xm.starter.umrp.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.QuerySysMenuDTO;
import com.xm.starter.umrp.model.dto.SysMenuInsertDTO;
import com.xm.starter.umrp.model.dto.SysMenuUpdateByIdDTO;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysMenuListVO;
import com.xm.starter.umrp.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    private @Autowired SysMenuService sysMenuService;

    @RequestMapping("/page")
    public PageVO<SysMenuListVO> page(@Valid  @RequestBody QuerySysMenuDTO query) {
        return sysMenuService.page(query);
    }

    @RequestMapping("/list")
    public List<SysMenuListVO> list(@Valid @RequestBody QuerySysMenuDTO query) {
        return sysMenuService.list(query);
    }

    @RequestMapping("/findById")
    public SysMenuDetailVO findById(Long id){
        return sysMenuService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        sysMenuService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@Valid @RequestBody SysMenuInsertDTO insertDTO){
        return sysMenuService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@Valid @RequestBody SysMenuUpdateByIdDTO updateByIdDTO){
        return sysMenuService.updateById(updateByIdDTO);
    }
}
