package com.xm.starter.area.service;

import com.xm.starter.area.model.*;
import com.xm.starter.base.model.PageVO;

import java.util.List;

public interface AreaService {
    PageVO<AreaListVO> page(QueryArealDTO query);
    List<AreaListVO> list(QueryArealDTO query);
    String insert(AreaInsertDTO insertDTO);
    String updateById(AreaUpdateByIdDTO updateByIdDTO);
    void deleteById(List<Long> id);
    AreaDetailVO findById(Long id);
}
