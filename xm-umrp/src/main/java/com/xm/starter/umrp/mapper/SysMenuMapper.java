package com.xm.starter.umrp.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.umrp.model.dto.QuerySysMenuDTO;
import com.xm.starter.umrp.model.po.SysMenuPO;
import com.xm.starter.umrp.model.vo.SysMenuListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends RootMapper<SysMenuPO> {
    List<SysMenuListVO> list(@Param("query") QuerySysMenuDTO query);
    MyBatisPlusPage<SysMenuListVO> page(MyBatisPlusPage<SysMenuListVO> page, @Param("query")QuerySysMenuDTO query);
}
