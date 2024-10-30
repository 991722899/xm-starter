package com.xm.starter.area.service;

import cn.hutool.core.bean.BeanUtil;
import com.xm.starter.area.mapper.AreaMapper;
import com.xm.starter.area.model.*;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.base.util.Assert;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    private @Autowired AreaMapper areaMapper;

    @Override
    public PageVO<AreaListVO> page(QueryArealDTO query) {
        return areaMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<AreaListVO> list(QueryArealDTO query) {
        return areaMapper.list(query);
    }

    @Override
    public String insert(AreaInsertDTO insertDTO) {
        AreaPO serialPO = new AreaPO();
        BeanUtils.copyProperties(insertDTO,serialPO);
        Assert.isTrue(areaMapper.insert(serialPO)>0,"新增失败");
        return serialPO.getCode().toString();
    }

    @Override
    public String updateById(AreaUpdateByIdDTO updateByIdDTO) {
        AreaPO serialPO = new AreaPO();
        BeanUtils.copyProperties(updateByIdDTO,serialPO);
        Assert.isTrue(areaMapper.updateById(serialPO)>0,"更新失败");
        return serialPO.getCode().toString();
    }

    @Override
    public void deleteById(List<Long> id) {
        Assert.isTrue(areaMapper.deleteByIds(id)==id.size(),"删除失败");
    }

    @Override
    public AreaDetailVO findById(Long id) {
        AreaPO serialPO = areaMapper.selectById(id);
        AreaDetailVO serialDetailVO = AreaDetailVO.builder().build();
        BeanUtil.copyProperties(serialPO,serialDetailVO);
        return serialDetailVO;
    }
}
