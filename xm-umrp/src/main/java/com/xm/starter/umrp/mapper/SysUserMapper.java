package com.xm.starter.umrp.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.umrp.model.dto.QuerySysUserDTO;
import com.xm.starter.umrp.model.po.SysUserPO;
import com.xm.starter.umrp.model.vo.SysUserListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends RootMapper<SysUserPO> {
    List<SysUserListVO> list(@Param("query") QuerySysUserDTO query);
    MyBatisPlusPage<SysUserListVO> page(MyBatisPlusPage<SysUserListVO> page, @Param("query")QuerySysUserDTO query);
}
