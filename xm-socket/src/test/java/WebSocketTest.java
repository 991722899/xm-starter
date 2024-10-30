import com.xm.starter.socket.config.SocketConfiguration;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@SpringBootTest(classes = {SocketConfiguration.class, WebSocketUserEventImplTest.class})
@RunWith(SpringRunner.class)
public class WebSocketTest  {
    @Test
    public void test() throws InterruptedException {
        Thread.sleep(2000L);
            try {
                WebSocketClient webSocketClient = new WebSocketClient( new URI("ws://127.0.0.1:8080/websocket")) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        System.out.println("open");
                    }

                    @Override
                    public void onMessage(String s) {
                        System.out.println("message");
                        this.send(s);
                        this.close();
                    }

                    @Override
                    public void onClose(int i, String s, boolean b) {
                        System.out.println("close");
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println("error");
                    }
                };

                webSocketClient.connect();

            }catch (Exception e){
                e.printStackTrace();
            }

        Thread.sleep(1200000L);
    }
}
