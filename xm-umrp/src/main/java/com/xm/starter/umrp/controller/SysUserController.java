package com.xm.starter.umrp.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.*;
import com.xm.starter.umrp.model.vo.*;
import com.xm.starter.umrp.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    private @Autowired SysUserService sysUserService;

    @RequestMapping("/login")
    public LoginSuccessVO login(LoginDTO loginDTO){
        return sysUserService.login(loginDTO);
    }

    @RequestMapping("/page")
    public PageVO<SysUserListVO> page(@Valid @RequestBody QuerySysUserDTO query) {
        return sysUserService.page(query);
    }

    @RequestMapping("/list")
    public List<SysUserListVO> list(@Valid @RequestBody QuerySysUserDTO query) {
        return sysUserService.list(query);
    }

    @RequestMapping("/findById")
    public SysUserDetailVO findById(Long id){
        return sysUserService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        sysUserService.deleteById(id);
    }

    @RequestMapping("/insert")
    public String insert(@Valid @RequestBody SysUserInsertDTO insertDTO){
        return sysUserService.insert(insertDTO);
    }

    @RequestMapping("/updateById")
    public String updateById(@Valid @RequestBody SysUserUpdateByIdDTO updateByIdDTO){
        return sysUserService.updateById(updateByIdDTO);
    }

    @RequestMapping("/findRoleByUserId")
    public List<SysRoleDetailVO> findRoleByUserId(Long id){
        return sysUserService.findRoleByUserId(id);
    }

    @RequestMapping("/findMenuByUserId")
    public List<SysMenuDetailVO> findMenuByUserId(Long id){
        return sysUserService.findMenuByUserId(id);
    }

    @RequestMapping("/updatePassword")
    public void updatePassword(SysUserUpdatePasswordDTO userUpdatePasswordDTO){
        sysUserService.updatePassword(userUpdatePasswordDTO);
    }
}
