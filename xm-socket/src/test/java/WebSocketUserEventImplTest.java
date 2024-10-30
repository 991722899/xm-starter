import com.xm.starter.socket.enums.LoginStatus;
import com.xm.starter.socket.event.WebSocketUserEvent;
import com.xm.starter.socket.model.SocketUser;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class WebSocketUserEventImplTest implements WebSocketUserEvent {

    @Override
    public SocketUser auth(FullHttpRequest fullHttpRequest) {
        SocketUser socketUser = new SocketUser();
        socketUser.setUserName("abcd");
        socketUser.setUserId("abcd");
        socketUser.setStatus(LoginStatus.SUCCESS.getCode());
        return socketUser;
    }
}
