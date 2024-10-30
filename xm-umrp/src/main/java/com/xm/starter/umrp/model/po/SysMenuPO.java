package com.xm.starter.umrp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_menu")
public class SysMenuPO extends BasePO {
    /**
     * 菜单名
     */
    private String menuName;
    /**
     * 菜单编码
     */
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
    private String menuParentId;
    /**
     * 状态 0正常 1禁用
     */
    private Integer menuStatus;
    /**
     * 类型 100菜单 200按钮 300接口
     */
    private Integer menuType;
}
