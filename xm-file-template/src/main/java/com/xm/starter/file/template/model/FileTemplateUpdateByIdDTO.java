package com.xm.starter.file.template.model;

import com.xm.starter.file.model.FileDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class FileTemplateUpdateByIdDTO extends FileTemplatePO{

    /**
     * 模板ID
     */
    @NotNull(message = "模板ID不能为空")
    private Long id;
    /**
     * 模板标题
     */
    @NotBlank(message = "模板标题不能为空")
    private String title;

    /**
     * 模板KEY
     */
    @NotBlank(message = "模板KEY不能为空")
    private String templateKey;

    /**
     * 备注
     */
    private String remarks;


    @NotEmpty(message = "模板文件不能为空")
    private List<FileDTO> fileDTOList;

}
