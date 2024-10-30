package com.xm.starter.umrp.model.vo;

import com.xm.starter.umrp.model.po.SysUserPO;
import lombok.Data;

import java.util.List;

@Data
public class SysUserDetailVO extends SysUserPO {
    private List<SysRoleDetailVO> roleDetailVOList;
}
