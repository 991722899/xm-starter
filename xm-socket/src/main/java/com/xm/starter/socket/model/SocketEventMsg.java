package com.xm.starter.socket.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Builder
@Data
public class SocketEventMsg {
    private MsgType msgType;
    private Object data;
    private LocalDateTime createTime;
    private Object ext;

    @Builder
    @Data
    public static class MsgType{
        private String group;
        private String type;
        private String code;
        private String msg;
    }
}
