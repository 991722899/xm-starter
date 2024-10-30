package com.xm.starter.serial.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.serial.model.*;

import java.util.List;

public interface SerialService {
    String generateCode(String serialKey);
    PageVO<SerialListVO> page(QuerySerialDTO query);
    List<SerialListVO> list(QuerySerialDTO query);
    String insert(SerialInsertDTO insertDTO);
    String updateById(SerialUpdateByIdDTO updateByIdDTO);
    void deleteById(List<Long> id);
    SerialDetailVO findById(Long id);
}
