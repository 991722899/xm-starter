package com.xm.starter.file.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.file.model.FileListVO;
import com.xm.starter.file.model.FilePO;
import com.xm.starter.file.model.QueryFileDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper extends RootMapper<FilePO> {
    List<FileListVO> list(@Param("query")QueryFileDTO query);
    MyBatisPlusPage<FileListVO> page(MyBatisPlusPage<FileListVO> page,@Param("query")QueryFileDTO query);
}
