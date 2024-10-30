package com.xm.starter.socket.model;

import com.xm.starter.socket.enums.LoginStatus;
import lombok.Data;

@Data
public class SocketUser {
    private String userId;
    private String userName;
    private String userRealName;
    private String deviceName;
    private String deviceId;
    /**
     * 100登录成功 200登录失败
     * {@link com.xm.starter.socket.enums.LoginStatus}
     */
    private Integer status = LoginStatus.SUCCESS.getCode();
    private String msg;
}
