package com.xm.starter.umrp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SysRoleUpdateByIdDTO {
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /**
     * 角色名
     */
    @NotBlank(message = "角色名不能为空")
    private String roleName;
    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;
    /**
     * 状态 0正常 1禁用
     */
    private Integer roleStatus;

    /**
     * 菜单id
     */
    @NotNull(message = "菜单不能为空")
    private List<Long> menuIdList;
}
