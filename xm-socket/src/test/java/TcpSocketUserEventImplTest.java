import com.xm.starter.socket.event.SocketUserEvent;
import com.xm.starter.socket.event.TcpSocketUserEvent;
import com.xm.starter.socket.model.SocketUser;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class TcpSocketUserEventImplTest implements TcpSocketUserEvent {
    @Override
    public SocketUser auth(String data) {
        SocketUser socketUser = new SocketUser();
        socketUser.setUserName("abcd");
        socketUser.setUserId("abcd");
        return socketUser;
    }
}
