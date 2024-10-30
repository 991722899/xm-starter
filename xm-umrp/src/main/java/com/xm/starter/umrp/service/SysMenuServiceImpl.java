package com.xm.starter.umrp.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.umrp.mapper.SysMenuMapper;
import com.xm.starter.umrp.model.dto.QuerySysMenuDTO;
import com.xm.starter.umrp.model.dto.SysMenuInsertDTO;
import com.xm.starter.umrp.model.dto.SysMenuUpdateByIdDTO;
import com.xm.starter.umrp.model.po.SysMenuPO;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysMenuListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    private @Autowired SysMenuMapper sysMenuMapper;


    @Override
    public void deleteById(List<Long> id) {
       sysMenuMapper.deleteByIds(id);
    }

    @Override
    public String insert(SysMenuInsertDTO insertDTO) {
        SysMenuPO sysMenuPO = new SysMenuPO();
        BeanUtils.copyProperties(insertDTO,sysMenuPO);
        sysMenuMapper.insert(sysMenuPO);
        return sysMenuPO.getId().toString();
    }

    @Override
    public String updateById(SysMenuUpdateByIdDTO updateByIdDTO) {
        SysMenuPO sysMenuPO = new SysMenuPO();
        BeanUtils.copyProperties(updateByIdDTO,sysMenuPO);
        sysMenuMapper.updateById(sysMenuPO);
        return sysMenuPO.getId().toString();
    }

    @Override
    public PageVO<SysMenuListVO> page(QuerySysMenuDTO query) {
        return sysMenuMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<SysMenuListVO> list(QuerySysMenuDTO query) {
        return sysMenuMapper.list(query);
    }

    @Override
    public SysMenuDetailVO findById(Long id) {
        SysMenuPO sysMenuPO = sysMenuMapper.selectById(id);
        SysMenuDetailVO detailVO = new SysMenuDetailVO();
        BeanUtils.copyProperties(sysMenuPO,detailVO);
       return detailVO;
    }
}
