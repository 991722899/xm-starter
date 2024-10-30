package com.xm.starter.file.template.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_file_template")
public class FileTemplatePO extends BasePO {
    /**
     * 模板标题
     */
    private String title;

    /**
     * 模板KEY
     */
    private String templateKey;

    /**
     * 文件批次号
     */
    private String fileBatchNo;

    /**
     * 备注
     */
    private String remarks;
}
