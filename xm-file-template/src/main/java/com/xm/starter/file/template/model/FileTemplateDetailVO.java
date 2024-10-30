package com.xm.starter.file.template.model;

import com.xm.starter.file.model.FileListVO;
import lombok.Data;

import java.util.List;

@Data
public class FileTemplateDetailVO{
    private List<FileListVO> files;
}
