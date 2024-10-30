package com.xm.starter.file.template.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.file.template.model.FileTemplateListVO;
import com.xm.starter.file.template.model.FileTemplatePO;
import com.xm.starter.file.template.model.QueryFileTemplateDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileTemplateMapper extends RootMapper<FileTemplatePO> {
    List<FileTemplateListVO> list(@Param("query") QueryFileTemplateDTO query);
    MyBatisPlusPage<FileTemplateListVO> page(MyBatisPlusPage<FileTemplateListVO> page, @Param("query")QueryFileTemplateDTO query);
}
