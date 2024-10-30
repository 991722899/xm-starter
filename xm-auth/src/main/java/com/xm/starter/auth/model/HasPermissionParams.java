package com.xm.starter.auth.model;

import com.xm.starter.base.model.UserInfo;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
@Data
public class HasPermissionParams {
    private UserInfo user;
    private HttpServletRequest request;

    public HasPermissionParams(UserInfo user, HttpServletRequest request) {
        this.user = user;
        this.request = request;
    }
}
