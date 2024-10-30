import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.mybatis.model.BasePO;
import com.xm.starter.task.config.TaskConfiguration;
import com.xm.starter.task.model.dto.TaskInsertDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

@EnableAutoConfiguration
@SpringBootTest(classes = {TaskConfiguration.class, TaskExecuteTest.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TaskTest {

    private @Autowired MockMvc mockMvc;
    private @Autowired ObjectMapper objectMapper;
    @Test
    public void  test() throws Exception {

        BasePO basePO = new BasePO();
        basePO.setCreateTime(LocalDateTime.now());
        basePO.setCreateId("123456");

        TaskInsertDTO taskInsertDTO = new TaskInsertDTO();
        taskInsertDTO.setCode("task_test");
        taskInsertDTO.setParams(objectMapper.writeValueAsString(basePO));
        System.out.println(mockMvc.perform(MockMvcRequestBuilders.post("/task/insert/task_test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskInsertDTO)))
                        .andReturn().getResponse().getStatus()==200);

        Thread.sleep(100000L);
    }
}
