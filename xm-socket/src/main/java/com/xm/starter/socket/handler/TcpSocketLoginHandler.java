package com.xm.starter.socket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.xm.starter.socket.enums.LoginStatus;
import com.xm.starter.socket.event.TcpSocketUserEvent;
import com.xm.starter.socket.model.SocketUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

public class TcpSocketLoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        TcpSocketUserEvent tcpSocketUserEvent  = SpringUtil.getBean(TcpSocketUserEvent.class);
        SocketUser socketUser = tcpSocketUserEvent.auth((String) msg);
        if(socketUser==null){
            socketUser = new SocketUser();
            socketUser.setStatus(LoginStatus.FAILURE.getCode());
            socketUser.setMsg("认证失败");
        }
        ctx.channel().attr(AttributeKey.valueOf("user")).set(socketUser);
        ctx.pipeline().remove(this);
        ctx.fireUserEventTriggered(socketUser);
    }
}
