package com.xm.starter.umrp.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.QuerySysRoleDTO;
import com.xm.starter.umrp.model.dto.SysRoleInsertDTO;
import com.xm.starter.umrp.model.dto.SysRoleUpdateByIdDTO;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleDetailVO;
import com.xm.starter.umrp.model.vo.SysRoleListVO;
import com.xm.starter.umrp.model.vo.SysUserDetailVO;

import java.util.List;

public interface SysRoleService {
    void deleteById(List<Long> id);
    String insert(SysRoleInsertDTO insertDTO);
    String updateById(SysRoleUpdateByIdDTO updateByIdDTO);
    PageVO<SysRoleListVO> page(QuerySysRoleDTO query);
    List<SysRoleListVO> list(QuerySysRoleDTO query);

    List<SysMenuDetailVO> findMenuByRoleId(Long id);

    List<SysUserDetailVO> findUserByRoleId(Long id);

    SysRoleDetailVO findById(Long id);
}
