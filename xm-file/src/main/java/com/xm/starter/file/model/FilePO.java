package com.xm.starter.file.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import com.xm.starter.file.enums.MainFileStatus;
import com.xm.starter.file.enums.StorageType;
import com.xm.starter.file.enums.TemporaryFileType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_file")
public class FilePO extends BasePO {
    /**
     * local本地,oss阿里云
     */
    private String storageType = StorageType.LOCAL.getType();
    /**
     * 唯一批次号
     */
    private String batchNo;
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
     * 新名称,文件存储名称
     */
    private String newName;
    /**
     * 存储地址
     */
    private String path;
    /**
     * 文件访问地址
     */
    private String accessUrl;
    /**
     * 文件访问地址过期时间
     */
    private LocalDateTime urlExpiration;
    /**
     * 文件长度(字节)
     */
    private Long length = 0L;
    /**
     * 图片宽度
     */
    private Integer width = 0;
    /**
     * 图片高度
     */
    private Integer height = 0;
    /**
     * 是否主图0否，1是
     */
    private Integer mainFile = MainFileStatus.NO.getValue();
    /**
     * 默认0为临时文件1非临时文件
     */
    private Integer temporaryFile = TemporaryFileType.TEMPORARY.getValue();
}
