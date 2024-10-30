package com.xm.starter.dict.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;
@TableName("sys_dict")
@Data
public class DictPo extends BasePO {
    /**
     * 字典项中文名称
     */
    @TableField(value = "dict_label")
    private String dictLabel;
    /**
     * 字典项值
     */
    @TableField(value = "dict_value")
    private String dictValue;

    /**
     * 上级ID
     */
    @TableField(value = "parent_id")
    private Long parentId = 0L;
}
