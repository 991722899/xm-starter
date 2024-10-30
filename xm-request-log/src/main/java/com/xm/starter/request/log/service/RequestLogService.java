package com.xm.starter.request.log.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.request.log.model.*;

import java.util.List;

public interface RequestLogService {
    void deleteById(List<Long> id);
    String insert(RequestLogInsertDTO insertDTO);
    String updateById(RequestLogUpdateByIdDTO updateByIdDTO);
    PageVO<RequestLogListVO> page(QueryRequestLogDTO query);
    List<RequestLogListVO> list(QueryRequestLogDTO query);
    RequestLogDetailVO findById(Long id);
}
