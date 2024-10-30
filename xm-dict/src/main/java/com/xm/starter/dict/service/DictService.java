package com.xm.starter.dict.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.dict.mapper.DictMapper;
import com.xm.starter.dict.model.*;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictService {
    private @Autowired DictMapper dictMapper;

    public List<DictListVO> list(QueryDictDTO query){
        return dictMapper.list(query);
    }

    public List<DictListVO> listTree(QueryDictDTO query){
        return listToTree(list(query),0L);
    }

    public PageVO<DictListVO> page(QueryDictDTO query) {
        return dictMapper.page(new MyBatisPlusPage<>(),query).toPageVO();
    }

    private List<DictListVO> listToTree(List<DictListVO> list,Long parentId){
        List<DictListVO> result = new ArrayList<>();
        for (DictListVO dictListVO : list) {
            if(dictListVO.getParentId().equals(parentId)){
                dictListVO.setChildren(listToTree(list,dictListVO.getId()));
                result.add(dictListVO);
            }
        }
        return result;
    }

    public DictDetailVO findById(Long id) {
        DictDetailVO dictDetailVO = new DictDetailVO();
        BeanUtils.copyProperties(dictMapper.selectById(id),dictDetailVO);
        return dictDetailVO;
    }

    public String insert(DictInsertDTO insertDTO) {
        DictPo dictPo = new DictPo();
        BeanUtils.copyProperties(insertDTO,dictPo);
        dictMapper.insert(dictPo);
        return dictPo.getId().toString();
    }

    public String updateById(DictUpdateDTO updateDTO) {
        DictPo dictPo = new DictPo();
        BeanUtils.copyProperties(updateDTO,dictPo);
        dictMapper.updateById(dictPo);
        return updateDTO.getId().toString();
    }
}
