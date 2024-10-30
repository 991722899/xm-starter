package com.xm.starter.umrp.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.umrp.model.dto.QuerySysMenuDTO;
import com.xm.starter.umrp.model.dto.SysMenuInsertDTO;
import com.xm.starter.umrp.model.dto.SysMenuUpdateByIdDTO;
import com.xm.starter.umrp.model.vo.SysMenuDetailVO;
import com.xm.starter.umrp.model.vo.SysMenuListVO;

import java.util.List;

public interface SysMenuService {
    void deleteById(List<Long> id);
    String insert(SysMenuInsertDTO insertDTO);
    String updateById(SysMenuUpdateByIdDTO updateByIdDTO);
    PageVO<SysMenuListVO> page(QuerySysMenuDTO query);
    List<SysMenuListVO> list(QuerySysMenuDTO query);
    SysMenuDetailVO findById(Long id);
}
