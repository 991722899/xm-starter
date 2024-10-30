package com.xm.starter.request.log.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.request.log.mapper.RequestLogMapper;
import com.xm.starter.request.log.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestLogServiceImpl implements RequestLogService {
    private @Autowired RequestLogMapper requestLogMapper;


    @Override
    public void deleteById(List<Long> id) {
       requestLogMapper.deleteByIds(id);
    }

    @Override
    public String insert(RequestLogInsertDTO insertDTO) {
        RequestLogPO requestLogPO = new RequestLogPO();
        BeanUtils.copyProperties(insertDTO,requestLogPO);
        requestLogMapper.insert(requestLogPO);
        return requestLogPO.getId().toString();
    }

    @Override
    public String updateById(RequestLogUpdateByIdDTO updateByIdDTO) {
        RequestLogPO requestLogPO = new RequestLogPO();
        BeanUtils.copyProperties(updateByIdDTO,requestLogPO);
        requestLogMapper.updateById(requestLogPO);
        return requestLogPO.getId().toString();
    }

    @Override
    public PageVO<RequestLogListVO> page(QueryRequestLogDTO query) {
        return requestLogMapper.page(new MyBatisPlusPage<RequestLogListVO>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<RequestLogListVO> list(QueryRequestLogDTO query) {
        return requestLogMapper.list(query);
    }

    @Override
    public RequestLogDetailVO findById(Long id) {
       RequestLogPO requestLogPO = requestLogMapper.selectById(id);
        RequestLogDetailVO detailVO = new RequestLogDetailVO();
        BeanUtils.copyProperties(requestLogPO,detailVO);
       return detailVO;
    }
}
