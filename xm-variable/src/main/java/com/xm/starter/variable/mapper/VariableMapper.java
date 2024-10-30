package com.xm.starter.variable.mapper;

import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.variable.model.QueryVariableDTO;
import com.xm.starter.variable.model.VariableListVO;
import com.xm.starter.variable.model.VariablePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VariableMapper extends RootMapper<VariablePO> {
    List<VariableListVO> list(@Param("query") QueryVariableDTO query);
    MyBatisPlusPage<VariableListVO> page(MyBatisPlusPage<VariableListVO> page, @Param("query")QueryVariableDTO query);
}
