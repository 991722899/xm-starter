package com.xm.starter.socket.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.socket.enums.LoginStatus;
import com.xm.starter.socket.model.SocketEventMsg;
import com.xm.starter.socket.model.SocketUser;
import com.xm.starter.socket.util.SocketUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.AttributeKey;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        msg.text();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);
        if(evt instanceof WebSocketServerProtocolHandler.ServerHandshakeStateEvent){
            if(((WebSocketServerProtocolHandler.ServerHandshakeStateEvent) evt).name().equals("HANDSHAKE_COMPLETE")){
                Object object =  ctx.channel().attr(AttributeKey.valueOf("user")).get();
                if(object!=null && object instanceof SocketUser){
                    SocketUser socketUser = (SocketUser) object;
                    if(socketUser.getStatus().equals(LoginStatus.SUCCESS.getCode())){
                        SocketUtil.addChannel(ctx.channel().id().asLongText(), ctx,socketUser);
                        ctx.channel().writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(SocketEventMsg.builder().msgType(SocketEventMsg.MsgType.builder().group("user").msg("登录成功").type("login").code("success").build()).build())));
                    }else{
                        ctx.channel().writeAndFlush(new TextWebSocketFrame(objectMapper.writeValueAsString(SocketEventMsg.builder().msgType(SocketEventMsg.MsgType.builder().group("user").msg(ObjectUtil.defaultIfBlank(socketUser.getMsg(),"登录失败")).type("login").code("error").build()).build())));
                    }
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        SocketUtil.removeChannel(ctx.channel().id().asLongText());
        super.handlerRemoved(ctx);
    }
}
