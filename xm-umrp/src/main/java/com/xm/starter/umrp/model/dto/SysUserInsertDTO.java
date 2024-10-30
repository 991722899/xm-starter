package com.xm.starter.umrp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SysUserInsertDTO {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;
    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    private String userRealName;
    /**
     * 密码，前端传md5加密后的值
     */
    @NotBlank(message = "密码不能为空")
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
    @NotNull(message = "状态不能为空")
    private Integer userStatus;

    /**
     * 角色id
     */
    private List<Long> roleIdList;
}
