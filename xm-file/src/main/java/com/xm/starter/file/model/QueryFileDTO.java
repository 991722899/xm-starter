package com.xm.starter.file.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class QueryFileDTO extends QueryPage {
    /**
      * 是否本地文件 0否 1是
     */
    private Integer localFile;
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
     * 是否主图0否，1是
     */
    private Integer mainFile;
    /**
     * 默认0为临时文件1非临时文件
     */
    private Integer temporaryFile;
    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 主键
     */
    private List<Long> id;
}
