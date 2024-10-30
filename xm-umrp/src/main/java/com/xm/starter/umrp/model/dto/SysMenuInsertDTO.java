package com.xm.starter.umrp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysMenuInsertDTO {
    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
    private String menuName;
    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    private String menuCode;
    /**
     * 路由
     */
    private String menuRouter;
    /**
     * 图标
     */
    private String menuIcon;
    /**
     * 父级菜单
     */
    @NotNull(message = "父级菜单不能为空")
    private String menuParentId;
    /**
     * 状态 0正常 1禁用
     */
    private Integer menuStatus;
    /**
     * 类型 100菜单 200按钮 300接口
     */
    @NotNull(message = "类型不能为空")
    private Integer menuType;
}
