package com.xm.starter.area.mapper;

import com.xm.starter.area.model.AreaListVO;
import com.xm.starter.area.model.AreaPO;
import com.xm.starter.area.model.QueryArealDTO;
import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper extends RootMapper<AreaPO> {
    List<AreaListVO> list(@Param("query") QueryArealDTO query);
    MyBatisPlusPage<AreaListVO> page(MyBatisPlusPage<AreaListVO> page, @Param("query") QueryArealDTO query);
}
