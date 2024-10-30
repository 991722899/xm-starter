package com.xm.starter.file.template.service;

import com.xm.starter.base.exception.BaseException;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.file.template.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public interface FileTemplateService {
    byte[] download(String templateKey) throws BaseException, IOException;
    void deleteById(List<Long> id);
    String insert(FileTemplateInsertDTO insertDTO);
    String updateById(FileTemplateUpdateByIdDTO updateByIdDTO);
    PageVO<FileTemplateListVO> page(QueryFileTemplateDTO query);
    List<FileTemplateListVO> list(QueryFileTemplateDTO query);
    FileTemplateDetailVO findById(Long id);
}
