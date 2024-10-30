package com.xm.starter.socket.model;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class SocketUserChannel {
    private String channelId;
    private ChannelHandlerContext channelHandlerContext;
    private String userId;
}
