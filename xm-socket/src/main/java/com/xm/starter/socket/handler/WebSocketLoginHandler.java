package com.xm.starter.socket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.mysql.cj.util.EscapeTokenizer;
import com.xm.starter.socket.enums.LoginStatus;
import com.xm.starter.socket.event.WebSocketUserEvent;
import com.xm.starter.socket.model.SocketUser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;

public class WebSocketLoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        WebSocketUserEvent webSocketUserEvent  = SpringUtil.getBean(WebSocketUserEvent.class);
        SocketUser socketUser = webSocketUserEvent.auth((FullHttpRequest) msg);
        if(socketUser==null){
            socketUser = new SocketUser();
            socketUser.setStatus(LoginStatus.FAILURE.getCode());
            socketUser.setMsg("认证失败");
        }
        ctx.channel().attr(AttributeKey.valueOf("user")).set(socketUser);
        ctx.pipeline().remove(this);
        super.channelRead(ctx, msg);
    }
}
