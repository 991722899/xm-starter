package com.xm.starter.test;

import com.xm.starter.auth.model.Auth;
import com.xm.starter.auth.model.HasPermissionParams;
import org.springframework.stereotype.Component;

@Component
public class AuthImpl implements Auth {
    @Override
    public boolean hasPermission(HasPermissionParams params) {
        return false;
    }
}
