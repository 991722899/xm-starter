package com.xm.starter.variable.service;

import cn.hutool.core.bean.BeanUtil;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.variable.mapper.VariableMapper;
import com.xm.starter.variable.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableServiceImpl implements VariableService {
    private @Autowired VariableMapper variableMapper;



    @Override
    public PageVO<VariableListVO> page(QueryVariableDTO query) {
        return variableMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<VariableListVO> list(QueryVariableDTO query) {
        return variableMapper.list(query);
    }

    @Override
    public String insert(VariableInsertDTO insertDTO) {
        VariablePO variablePO = new VariablePO();
        BeanUtils.copyProperties(insertDTO,variablePO);
        Assert.isTrue(variableMapper.insert(variablePO)>0,"新增失败");
        return variablePO.getId().toString();
    }

    @Override
    public String updateById(VariableUpdateByIdDTO updateByIdDTO) {
        VariablePO variablePO = new VariablePO();
        BeanUtils.copyProperties(updateByIdDTO,variablePO);
        Assert.isTrue(variableMapper.updateById(variablePO)>0,"更新失败");
        return variablePO.getId().toString();
    }

    @Override
    public void deleteById(List<Long> id) {
        Assert.isTrue(variableMapper.deleteByIds(id)==id.size(),"删除失败");
    }

    @Override
    public VariableDetailVO findById(Long id) {
        VariablePO VariablePO = variableMapper.selectById(id);
        VariableDetailVO variableDetailVO = new VariableDetailVO();
        BeanUtil.copyProperties(VariablePO,variableDetailVO);
        return variableDetailVO;
    }
}
