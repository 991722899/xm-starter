package com.xm.starter.umrp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_role")
public class SysRolePO extends BasePO {
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
}
