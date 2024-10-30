package com.xm.starter.base.api;

import com.xm.starter.base.model.UserInfo;
import org.springframework.core.Ordered;

public class DefaultUserService implements UserService, Ordered {
    @Override
    public String userId() {
        return "-1";
    }

    @Override
    public String userName() {
        return "系统默认";
    }

    @Override
    public String userRealName() {
        return "系统默认";
    }

    @Override
    public UserInfo<UserInfo> user() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(this.userName());
        userInfo.setUserName(this.userId());
        userInfo.setUserRealName(this.userRealName());
        return userInfo;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
