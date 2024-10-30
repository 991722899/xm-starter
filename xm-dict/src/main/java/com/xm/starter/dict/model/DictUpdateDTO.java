package com.xm.starter.dict.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DictUpdateDTO{
    @NotNull(message = "字典ID不能为空")
    private Long id;
    /**
     * 字典项中文名称
     */
    @NotBlank(message = "字典项中文名称不能为空")
    private String dictLabel;
    /**
     * 字典项值
     */
    @NotBlank(message = "字典项值不能为空")
    private String dictValue;

    /**
     * 上级ID
     */
    private Long parentId = 0L;
}
