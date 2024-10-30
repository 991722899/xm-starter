package com.xm.starter.request.log.service;

import cn.hutool.core.collection.CollUtil;
import com.xm.starter.request.log.mapper.RequestLogDetailMapper;
import com.xm.starter.request.log.mapper.RequestLogMapper;
import com.xm.starter.request.log.model.RequestLogDetailOutputDTO;
import com.xm.starter.request.log.model.RequestLogDetailPO;
import com.xm.starter.request.log.model.RequestLogOutputDTO;
import com.xm.starter.request.log.model.RequestLogPO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestLogOutputServiceImpl implements RequestLogOutputService {
    private @Autowired RequestLogMapper requestLogMapper;
    private @Autowired RequestLogDetailMapper requestLogDetailMapper;

    @Override
    public void output(RequestLogOutputDTO requestLogOutputDTO) {
        RequestLogPO requestLogPO = new RequestLogPO();
        BeanUtils.copyProperties(requestLogOutputDTO,requestLogPO);
        requestLogMapper.insert(requestLogPO);

        if(CollUtil.isNotEmpty(requestLogOutputDTO.getDetailList())){
            List<RequestLogDetailPO> detailPOList = new ArrayList<>();
            requestLogOutputDTO.getDetailList().forEach(detail -> {
                RequestLogDetailPO detailPO = new RequestLogDetailOutputDTO();
                BeanUtils.copyProperties(detail,detailPO);
                detailPO.setRequestLogId(requestLogPO.getId());
                detailPOList.add(detailPO);
            });
            requestLogDetailMapper.insert(detailPOList);
        }
    }
}
