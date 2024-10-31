import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.request.log.config.RequestLogConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@EnableAutoConfiguration
@SpringBootTest(classes = {RequestLogConfiguration.class})
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RequestLogTest {
    private @Autowired MockMvc mockMvc;
    private @Autowired ObjectMapper objectMapper;

    @Test
    public void test() throws Exception {
        MockHttpServletResponse mockHttpServletResponse = mockMvc.perform(MockMvcRequestBuilders.post("/task/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("")))
                .andReturn().getResponse();
        System.out.println(mockHttpServletResponse.getContentAsString());
    }
}
