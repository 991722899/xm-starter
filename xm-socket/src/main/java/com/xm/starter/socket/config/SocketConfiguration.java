package com.xm.starter.socket.config;

import com.xm.starter.socket.handler.ChooseProtocolHandler;
import com.xm.starter.socket.model.SocketProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
@Slf4j
@Configuration
@ComponentScan(value = "com.xm.starter.socket")
@MapperScan(value = "com.xm.starter.socket.mapper")
@ConditionalOnProperty(prefix = "xm.starter.socket",name = "enable",havingValue = "true")
@EnableConfigurationProperties(SocketProperties.class)
public class SocketConfiguration implements CommandLineRunner {
    private @Autowired SocketProperties socketProperties;

    private static final Logger log = LoggerFactory.getLogger(SocketConfiguration.class);

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture.runAsync(()->{
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline p = ch.pipeline();
                                p.addLast(new ChooseProtocolHandler());
                            }
                        });
                ChannelFuture f = b.bind(socketProperties.getHost(),socketProperties.getPort()).sync();
                f.channel().closeFuture().sync();
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });

    }
}
