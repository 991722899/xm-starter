package com.xm.starter.umrp.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class SysRoleDetailVO{
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 状态 0正常 1禁用
     */
    private Integer roleStatus;

    /**
     * 角色对应的菜单
     */
    private List<SysMenuDetailVO> menuDetailVOList;
}
