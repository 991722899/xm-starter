package com.xm.starter.umrp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_user_role")
public class SysUserRolePO extends BasePO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;
}
