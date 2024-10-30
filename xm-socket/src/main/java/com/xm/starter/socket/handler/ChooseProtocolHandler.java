package com.xm.starter.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
* @description：协议选择 tcp websocket
* @author：陈超超
* @time：2024/7/11 10:29
*/
public class ChooseProtocolHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        byteBuf.markReaderIndex();
        // 读取请求行，假设请求行长度不超过1024字节
        byte[] requestLineBytes = new byte[1024];
        int readableBytes = Math.min(byteBuf.readableBytes(), requestLineBytes.length);
        byteBuf.readBytes(requestLineBytes, 0, readableBytes);
        String requestLine = new String(requestLineBytes, 0, readableBytes, StandardCharsets.US_ASCII).trim();
        byteBuf.resetReaderIndex();
        // 检查请求行是否为GET请求
        if (requestLine.contains("Connection: Upgrade")) {
            ctx.pipeline().addLast(new HttpServerCodec());
            ctx.pipeline().addLast(new HttpObjectAggregator(65536));
            ctx.pipeline().addLast(new ChunkedWriteHandler());
            ctx.pipeline().addLast(new WebSocketLoginHandler());
            ctx.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
            ctx.pipeline().addLast(new WebSocketHandler());
        } else {
            ctx.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
            ctx.pipeline().addLast(new LengthFieldPrepender(4));
            ctx.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ctx.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            ctx.pipeline().addLast(new TcpSocketLoginHandler());
            ctx.pipeline().addLast(new TcpSocketHandler());
        }
        ctx.pipeline().remove(this);
    }
}
