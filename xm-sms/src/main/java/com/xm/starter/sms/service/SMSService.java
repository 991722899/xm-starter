package com.xm.starter.sms.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.sms.model.*;

import java.util.List;

public interface SMSService {
    PageVO<SMSListVO> page(QuerySMSDTO query);
    List<SMSListVO> list(QuerySMSDTO query);
    String insert(SMSInsertDTO insertDTO);
    String updateById(SMSUpdateByIdDTO updateByIdDTO);
    void deleteById(List<Long> id);
    SMSDetailVO findById(Long id);
    void send(SMSSendDTO sendDTO) throws Exception;
}
