package com.xm.starter.sms.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.sms.model.QuerySMSDTO;
import com.xm.starter.sms.model.SMSListVO;
import com.xm.starter.sms.model.SMSPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SMSMapper extends RootMapper<SMSPO> {
    List<SMSListVO> list(@Param("query") QuerySMSDTO query);
    MyBatisPlusPage<SMSListVO> page(MyBatisPlusPage<SMSListVO> page, @Param("query")QuerySMSDTO query);
}
