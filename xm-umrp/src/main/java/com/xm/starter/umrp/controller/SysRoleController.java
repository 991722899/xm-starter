package com.xm.starter.umrp.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.QuerySysRoleDTO;
import com.xm.starter.umrp.model.dto.SysRoleInsertDTO;
import com.xm.starter.umrp.model.dto.SysRoleUpdateByIdDTO;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleListVO;
import com.xm.starter.umrp.model.vo.SysUserDetailVO;
import com.xm.starter.umrp.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    private @Autowired SysRoleService sysRoleService;

    @RequestMapping("/page")
    public PageVO<SysRoleListVO> page(@Valid @RequestBody QuerySysRoleDTO query) {
        return sysRoleService.page(query);
    }

    @RequestMapping("/list")
    public List<SysRoleListVO> list(@Valid @RequestBody QuerySysRoleDTO query) {
        return sysRoleService.list(query);
    }

    @RequestMapping("/findById")
    public SysRoleDetailVO findById(Long id){
        return sysRoleService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        sysRoleService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@Valid @RequestBody SysRoleInsertDTO insertDTO){
        return sysRoleService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@Valid @RequestBody SysRoleUpdateByIdDTO updateByIdDTO){
        return sysRoleService.updateById(updateByIdDTO);
    }

    @RequestMapping("/findMenuByRoleId")
    public List<SysMenuDetailVO> findMenuByRoleId(Long id){
        return sysRoleService.findMenuByRoleId(id);
    }
    @RequestMapping("/findUserByRoleId")
    public List<SysUserDetailVO> findUserByRoleId(Long id){
        return sysRoleService.findUserByRoleId(id);
    }
}
