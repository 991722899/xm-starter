package com.xm.starter.socket.event;

import com.xm.starter.socket.model.SocketUser;
import io.netty.handler.codec.http.FullHttpRequest;

public interface TcpSocketUserEvent extends SocketUserEvent {
    SocketUser auth(String data);
}
