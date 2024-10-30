package com.xm.starter.auth.service;

import cn.hutool.jwt.JWT;
import com.xm.starter.auth.model.AuthProperties;
import com.xm.starter.base.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class AuthSupportService {
    private @Autowired AuthProperties authProperties;

    /**
     * 创建token
     * @param user
     */
    public void createToken(UserInfo user){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            HttpServletResponse response = requestAttributes.getResponse();
            String token = JWT.create()
                    .setPayload("user",user)
                    .setExpiresAt( Date.from(LocalDateTime.now().plusSeconds(authProperties.getExpirationTime()).atZone(ZoneId.systemDefault()).toInstant()))
                    .setSigner("HS256", authProperties.getJwtSecret().getBytes())
                    .sign(); // 生成JWT字符串
            response.setHeader(authProperties.getTokenHeader(),token);
        }
    }
}
