package com.xm.starter.auth.model;
/**
* @description：请求认证拦截，由集成者实现
* @author：陈超超
* @time：2024/10/25 16:21
*/
public interface Auth {
    boolean hasPermission(HasPermissionParams params);
}
