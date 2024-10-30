package com.xm.starter.request.log.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.request.log.model.QueryRequestLogDTO;
import com.xm.starter.request.log.model.RequestLogListVO;
import com.xm.starter.request.log.model.RequestLogPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RequestLogMapper extends RootMapper<RequestLogPO> {
    List<RequestLogListVO> list(@Param("query") QueryRequestLogDTO query);
    MyBatisPlusPage<RequestLogListVO> page(MyBatisPlusPage<RequestLogListVO> page, @Param("query")QueryRequestLogDTO query);
}
