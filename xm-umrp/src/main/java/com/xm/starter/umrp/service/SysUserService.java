package com.xm.starter.umrp.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.*;
import com.xm.starter.umrp.model.vo.*;

import java.util.List;

public interface SysUserService {
    void deleteById(List<Long> id);
    String insert(SysUserInsertDTO insertDTO);
    String updateById(SysUserUpdateByIdDTO updateByIdDTO);
    void updatePassword(SysUserUpdatePasswordDTO updatePasswordDTO);
    PageVO<SysUserListVO> page(QuerySysUserDTO query);
    List<SysUserListVO> list(QuerySysUserDTO query);
    List<SysRoleDetailVO> findRoleByUserId(Long id);

    List<SysMenuDetailVO> findMenuByUserId(Long id);

    SysUserDetailVO findById(Long id);

    LoginSuccessVO login(LoginDTO loginDTO);
}
