package com.xm.starter.variable.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.variable.model.*;

import java.util.List;

public interface VariableService {
    PageVO<VariableListVO> page(QueryVariableDTO query);
    List<VariableListVO> list(QueryVariableDTO query);
    String insert(VariableInsertDTO insertDTO);
    String updateById(VariableUpdateByIdDTO updateByIdDTO);
    void deleteById(List<Long> id);
    VariableDetailVO findById(Long id);
}
