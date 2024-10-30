package com.xm.starter.dict.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DictInsertDTO{
    /**
     * 字典项中文名称
     */
    @NotNull(message = "字典项中文名称不能为空")
    private String dictLabel;
    /**
     * 字典项值
     */
    @NotNull(message = "字典项值不能为空")
    private String dictValue;

    /**
     * 上级ID
     */
    @NotNull(message = "上级ID不能为空")
    private Long parentId = 0L;
}
