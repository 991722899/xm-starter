import com.xm.starter.business.log.config.BusinessLogConfiguration;
import com.xm.starter.business.log.controller.BusinessLogController;
import com.xm.starter.business.log.mapper.BusinessLogMapper;
import com.xm.starter.business.log.model.BusinessLogDetailVO;
import com.xm.starter.business.log.model.BusinessLogInsertDto;
import com.xm.starter.business.log.service.BusinessLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = {BusinessLogConfiguration.class,BusinessLogHandlerTest.class})
@AutoConfigureMockMvc
public class BusinessLogTest {
    private @Autowired BusinessLogController dictController;
    private @Autowired BusinessLogService dictService;
    private @Autowired BusinessLogMapper dictMapper;
    private @Autowired  MockMvc mockMvc;
    private @Autowired ModelMapper modelMapper;
    private @Autowired BusinessLogHandlerTest businessLogHandlerTest;


    @Test
    public void test() throws Exception {
        BusinessLogInsertDto businessLogInsertDto = new BusinessLogInsertDto();
        businessLogInsertDto.setBusinessNo("ABCD");
        BusinessLogDetailVO businessLogDetailVO = new BusinessLogDetailVO();
        businessLogHandlerTest.test(mockMvc,businessLogInsertDto,businessLogDetailVO);
        System.out.println(111);
    }
}
