package com.xm.starter.socket.event;

import com.xm.starter.socket.model.SocketUser;
import io.netty.handler.codec.http.FullHttpRequest;

public interface WebSocketUserEvent extends SocketUserEvent {
    SocketUser auth(FullHttpRequest fullHttpRequest);
}
