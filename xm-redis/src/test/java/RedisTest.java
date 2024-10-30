import com.xm.starter.base.query.QueryPage;
import com.xm.starter.redis.config.RedisConfiguration;
import com.xm.starter.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@EnableAutoConfiguration
@SpringBootTest(classes = {RedisConfiguration.class})
@RunWith(SpringRunner.class)
public class RedisTest {
    private @Autowired RedisService redisService;

    @Test
    public void  test(){
        QueryPage basePO = new QueryPage();
        basePO.setPageNum(1L);
        basePO.setPageSize(10L);
        redisService.opsForValue().set("basePO",basePO);
        QueryPage  po3= redisService.get("basePO",QueryPage.class);
        System.out.println(po3);
    }
}
