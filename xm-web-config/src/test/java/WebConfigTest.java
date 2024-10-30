import com.xm.starter.web.config.WebConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootTest(classes = {WebConfiguration.class})
@RunWith(SpringRunner.class)
public class WebConfigTest {

    @Test
    public void  test() throws Exception {


    }
}
