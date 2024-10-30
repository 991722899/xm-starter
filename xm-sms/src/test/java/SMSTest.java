import com.xm.starter.sms.config.SMSConfiguration;
import com.xm.starter.sms.model.SMSSendDTO;
import com.xm.starter.sms.service.SMSService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration
@SpringBootTest(classes = {SMSConfiguration.class})
@RunWith(SpringRunner.class)
public class SMSTest {
    private @Autowired SMSService smsService;

    @Test
    public void  test() throws Exception {
        Map<String,Object> params = new HashMap<>();
        params.put("projectName","123456");
        params.put("formingDate","2024-07-09");
        params.put("value","680");
        params.put("checkListNo","20240709123456");


        smsService.send(SMSSendDTO.builder().phone("18320875382").templateCode("SMS_220626349").signName("混凝土试块管理").templateParam(params).build());
    }
}
