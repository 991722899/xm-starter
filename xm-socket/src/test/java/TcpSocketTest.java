import com.xm.starter.socket.config.SocketConfiguration;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootTest(classes = {SocketConfiguration.class, TcpSocketUserEventImplTest.class})
@RunWith(SpringRunner.class)
public class TcpSocketTest {
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(2000L);
        new Thread(() -> {
            // 配置客户端NIO线程组
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap(); // 客户端启动对象
                b.group(group)
                        .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                        .handler(new ChannelInitializer<SocketChannel>() { // 给pipeline设置处理器
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                // 添加字符串编码和解码处理器
                                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                                ch.pipeline().addLast(new LengthFieldPrepender(4));
                                ch.pipeline().addLast(new StringEncoder());
                                ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
                                // 添加自定义的客户端处理器
                                ch.pipeline().addLast(new ClientHandler());
                            }
                        });

                // 发起异步连接操作
                ChannelFuture f = b.connect("127.0.0.1", 8080).sync();
                // 等待客户端链路关闭
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // 优雅退出，释放NIO线程组
                group.shutdownGracefully();
            }
        }).start();

        Thread.sleep(100000L);
    }

    public static class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            super.handlerAdded(ctx);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Connected to server.");

            // 获取Channel，用于发送消息
            Channel channel = ctx.channel();
            // 发送消息
            ChannelFuture channelFuture = channel.writeAndFlush("Hello from client!");
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("Message sent successfully.");
                    } else {
                        System.out.println("Failed to send message.");
                    }
                }
            });
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            msg.getClass();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
