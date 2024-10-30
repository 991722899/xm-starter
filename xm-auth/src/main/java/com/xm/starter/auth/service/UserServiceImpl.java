package com.xm.starter.auth.service;

import com.xm.starter.auth.UserHold;
import com.xm.starter.base.api.UserService;
import com.xm.starter.base.model.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{
    @Override
    public String userId() {
        return UserHold.getUser().getUserId();
    }

    @Override
    public String userName() {
        return UserHold.getUser().getUserName();
    }

    @Override
    public String userRealName() {
        return UserHold.getUser().getUserName();
    }

    @Override
    public UserInfo user() {
        return UserHold.getUser();
    }
}
