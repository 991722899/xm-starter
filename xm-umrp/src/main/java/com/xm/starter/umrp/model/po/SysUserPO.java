package com.xm.starter.umrp.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;

@Data
@TableName("sys_user")
public class SysUserPO extends BasePO {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 真实姓名
     */
    private String userRealName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 手机号
     */
    private String userPhone;
    /**
     * 状态 0正常 1禁用
     */
    private Integer userStatus;
    /**
     * 密码盐值
     */
    private String saltValue;
}
