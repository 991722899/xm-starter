package com.xm.starter.umrp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_role_menu")
public class SysRoleMenuPO extends BasePO {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 菜单id
     */
    private Long menuId;
}
