package com.xm.starter.business.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.starter.business.log.model.BusinessLogListVO;
import com.xm.starter.business.log.model.BusinessLogPo;
import com.xm.starter.business.log.model.QueryBusinessLogDTO;
import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusinessLogMapper extends RootMapper<BusinessLogPo> {
    List<BusinessLogListVO> list(@Param("query") QueryBusinessLogDTO query);

    MyBatisPlusPage<BusinessLogListVO> page(MyBatisPlusPage<BusinessLogListVO> page, @Param("query") QueryBusinessLogDTO query);
}
