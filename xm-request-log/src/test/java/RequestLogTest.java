import com.xm.starter.request.log.config.RequestLogConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@SpringBootTest(classes = {RequestLogConfiguration.class})
@RunWith(SpringRunner.class)
public class RequestLogTest {

    @Test
    public void test(){

    }
}
