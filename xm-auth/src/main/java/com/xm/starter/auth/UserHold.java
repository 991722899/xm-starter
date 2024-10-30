package com.xm.starter.auth;

import com.xm.starter.base.model.UserInfo;
import lombok.Data;
/**
* @description：登录成功后存储
* @author：陈超超
* @time：2024/6/12 12:12
*/
@Data
public final class UserHold {
    private final static ThreadLocal<UserInfo> userThreadLocal = new ThreadLocal<>();

    public static void setUser(UserInfo user) {
        userThreadLocal.set(user);
    }

    public static UserInfo getUser() {
        return userThreadLocal.get();
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}
