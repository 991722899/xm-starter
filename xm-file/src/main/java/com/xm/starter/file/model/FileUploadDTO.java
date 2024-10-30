package com.xm.starter.file.model;

import lombok.Data;

import java.io.File;

@Data
public class FileUploadDTO {
    /**
     * 业务类型定义唯一KEY，表示此数据为某个模块某个字段的文件
     */
    private String businessType;
    /**
     * 业务ID
     */
    private Long businessId;
    /**
     * 业务编号
     */
    private String businessNo;

    private File file;


    public FilePO toFilePO(){
        FilePO filePO = new FilePO();
        filePO.setBusinessType(businessType);
        filePO.setBusinessId(businessId);
        filePO.setBusinessNo(businessNo);
        return filePO;
    }
}
