import com.xm.starter.auth.config.AuthConfiguration;
import com.xm.starter.base.api.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@SpringBootTest(classes = {AuthConfiguration.class})
@RunWith(SpringRunner.class)
public class UserServiceTest {
    private @Autowired UserService userService;

    @Test
    public void test(){
        userService.user();
    }
}
