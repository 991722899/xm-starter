import com.xm.starter.log.visual.config.LogVisualConfiguration;
import com.xm.starter.log.visual.model.LogVisualReadDTO;
import com.xm.starter.log.visual.model.LogVisualReadVO;
import com.xm.starter.log.visual.service.LogVisualService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootTest(classes = {LogVisualConfiguration.class})
@RunWith(SpringRunner.class)
public class LogVisualTest {
    private @Autowired LogVisualService logVisualService;

    @Test
    public void read() throws IOException, InterruptedException {
        LogVisualReadDTO logVisualReadDTO = new LogVisualReadDTO();
        logVisualReadDTO.setPath("E:\\logs\\sc-scm\\all.2022-07-28.0.log");
        logVisualReadDTO.setOffset(2048L);
        LogVisualReadVO logVisualReadVO = logVisualService.read(logVisualReadDTO);
        System.out.println(logVisualReadVO);
        Thread.sleep(100000L);
    }
}
