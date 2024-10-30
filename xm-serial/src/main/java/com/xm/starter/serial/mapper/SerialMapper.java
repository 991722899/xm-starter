package com.xm.starter.serial.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.serial.model.QuerySerialDTO;
import com.xm.starter.serial.model.SerialListVO;
import com.xm.starter.serial.model.SerialPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SerialMapper extends RootMapper<SerialPO> {
    List<SerialListVO> list(@Param("query") QuerySerialDTO query);
    MyBatisPlusPage<SerialListVO> page(MyBatisPlusPage<SerialListVO> page, @Param("query")QuerySerialDTO query);
    int next(@Param("serialKey") String serialKey);
}
