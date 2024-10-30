package com.xm.starter.umrp.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SysUserUpdatePasswordDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    @NotBlank(message = "密码不能为空")
    private String password;
}
