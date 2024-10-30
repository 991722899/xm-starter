package com.xm.starter.file.model;

import lombok.Builder;
import lombok.Data;

import java.io.InputStream;

@Builder
@Data
public class FileInputStreamUploadDTO {
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
    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 文件原始名称
     */
    private String originalName;

    /**
     * 文件内容
     */
    private InputStream inputStream;

    public FilePO toFilePO(){
        FilePO filePO  = new FilePO();
        filePO.setBusinessType(businessType);
        filePO.setBusinessId(businessId);
        filePO.setBusinessNo(businessNo);
        filePO.setContentType(contentType);
        filePO.setOriginalName(originalName);
        return filePO;
    }
}
