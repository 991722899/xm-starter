package com.xm.starter.business.log.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.business.log.mapper.BusinessDetailLogMapper;
import com.xm.starter.business.log.mapper.BusinessLogMapper;
import com.xm.starter.business.log.model.*;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessLogService {
    private @Autowired BusinessLogMapper businessLogMapper;
    private @Autowired BusinessDetailLogMapper businessDetailLogMapper;

    public List<BusinessLogListVO> list(QueryBusinessLogDTO query){
        return businessLogMapper.list(query);
    }

    public BusinessLogDetailVO detailByLogId(Long id){
        BusinessLogPo businessLogPo = businessLogMapper.selectById(id);
        BusinessLogDetailVO detailVO = new BusinessLogDetailVO();
        BeanUtils.copyProperties(businessLogPo,detailVO);
        List<BusinessDetailLogPO> detailPOS = businessDetailLogMapper.selectList(new LambdaQueryWrapper<BusinessDetailLogPO>().eq(BusinessDetailLogPO::getLogId,id));
        detailVO.setDetails(listToTree(detailPOS,0L));
        return detailVO;
    }

    public PageVO<BusinessLogListVO> page(QueryBusinessLogDTO query) {
        return businessLogMapper.page(new MyBatisPlusPage<>(),query).toPageVO();
    }

    private List<BusinessDetailLogListVo> listToTree(List<BusinessDetailLogPO> list, Long parentId){
        List<BusinessDetailLogListVo> result = new ArrayList<>();
        for (BusinessDetailLogPO detailPO : list) {
            if(detailPO.getParentId().equals(parentId)){
                BusinessDetailLogListVo detailListVo = new BusinessDetailLogListVo();
                BeanUtils.copyProperties(detailPO,detailListVo);
                detailListVo.setChildren(listToTree(list,detailPO.getId()));
                result.add(detailListVo);
            }
        }
        return result;
    }

    public BusinessLogDetailVO findById(Long id) {
        BusinessLogDetailVO dictDetailVO = new BusinessLogDetailVO();
        BeanUtils.copyProperties(businessLogMapper.selectById(id),dictDetailVO);
        return dictDetailVO;
    }

    public String insert(BusinessLogInsertDto insertDTO) {
        BusinessLogPo dictPo = new BusinessLogPo();
        BeanUtils.copyProperties(insertDTO,dictPo);
        businessLogMapper.insert(dictPo);
        return dictPo.getId().toString();
    }

    public String updateById(BusinessLogUpdateDTO updateDTO) {
        BusinessLogPo dictPo = new BusinessLogPo();
        BeanUtils.copyProperties(updateDTO,dictPo);
        businessLogMapper.updateById(dictPo);
        return updateDTO.getId().toString();
    }
}
