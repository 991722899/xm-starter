package com.xm.starter.auth.interceptor;

import cn.hutool.jwt.JWTUtil;
import com.xm.starter.auth.UserHold;
import com.xm.starter.auth.model.Auth;
import com.xm.starter.auth.model.AuthProperties;
import com.xm.starter.auth.model.HasPermissionParams;
import com.xm.starter.base.exception.BaseException;
import com.xm.starter.base.model.ResCode;
import com.xm.starter.base.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @description：处理认证
* @author：陈超超
* @time：2024/6/12 12:22
*/
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private @Autowired AuthProperties authProperties;
    private @Autowired Auth auth;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = JWTUtil.verify(request.getHeader(authProperties.getTokenHeader()),authProperties.getJwtSecret().getBytes());
        if(!flag){
            throw new BaseException("token验证失败", ResCode.UNAUTHORIZED);
        }

        UserInfo user = JWTUtil.parseToken(request.getHeader(authProperties.getTokenHeader())).getPayload().getClaimsJson().get("user", UserInfo.class);

        if(!auth.hasPermission(new HasPermissionParams(user,request))){
            throw new BaseException("没有权限", ResCode.FORBIDDEN);
        }
        UserHold.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserHold.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
