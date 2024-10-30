package com.xm.starter.umrp.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.umrp.model.dto.QuerySysRoleDTO;
import com.xm.starter.umrp.model.po.SysRolePO;
import com.xm.starter.umrp.model.vo.SysRoleListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends RootMapper<SysRolePO> {
    List<SysRoleListVO> list(@Param("query") QuerySysRoleDTO query);
    MyBatisPlusPage<SysRoleListVO> page(MyBatisPlusPage<SysRoleListVO> page, @Param("query")QuerySysRoleDTO query);
}
