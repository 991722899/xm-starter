import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.dict.config.DictConfiguration;
import com.xm.starter.dict.controller.DictController;
import com.xm.starter.dict.mapper.DictMapper;
import com.xm.starter.dict.model.DictDetailVO;
import com.xm.starter.dict.model.DictInsertDTO;
import com.xm.starter.dict.model.DictPo;
import com.xm.starter.dict.model.QueryDictDTO;
import com.xm.starter.dict.service.DictService;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = DictConfiguration.class)
@AutoConfigureMockMvc
public class DictTest {
    private @Autowired DictController dictController;
    private @Autowired DictService dictService;
    private @Autowired DictMapper dictMapper;
    private @Autowired  MockMvc mockMvc;
    private @Autowired ModelMapper modelMapper;

    @Test
    public void insert() throws Exception {
        System.out.println(mockMvc.perform(post("/dict/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new DictInsertDTO())))
                .andReturn().getResponse().getContentAsString());
    }

    @Test
    public void findById(){
        dictService.findById(1798607795902451713L);
    }
    @Test
    public void listTree(){
        dictService.listTree(new QueryDictDTO());
    }
    @Test
    public void page(){
        dictMapper.page(new MyBatisPlusPage<>(1L,1L),new QueryDictDTO()).toPageVO();
    }

    @Test
    public void test() throws Exception {


        DictPo dictPo = new DictPo();
        dictPo.setDictLabel("是否");
        dictPo.setDictValue("YES_NO");
        dictPo.setSort(1);
        dictPo.setCreateId("1");
        dictPo.setCreateName("1");
        dictPo.setCreateTime(LocalDateTime.now());
        dictMapper.insert(dictPo);

        DictPo dictPo2 = new DictPo();
        dictPo2.setDictLabel("否");
        dictPo2.setDictValue("0");
        dictPo2.setParentId(dictPo.getId());
        dictPo2.setSort(1);
        dictPo2.setCreateId("1");
        dictPo2.setCreateName("1");
        dictPo2.setCreateTime(LocalDateTime.now());

        DictPo dictPo3 = new DictPo();
        dictPo3.setDictLabel("是");
        dictPo3.setDictValue("1");
        dictPo3.setParentId(dictPo.getId());
        dictPo3.setSort(1);
        dictPo3.setCreateId("1");
        dictPo3.setCreateName("1");
        dictPo3.setCreateTime(LocalDateTime.now());



        dictMapper.insert(dictPo2);
        dictMapper.insert(dictPo3);
        dictService.listTree(new QueryDictDTO());
    }

    @Test
    public void pOToVo(){
        DictPo dictPo = new DictPo();
        dictPo.setDictValue("test");

        DictDetailVO dictDetailVO = new DictDetailVO();
        modelMapper.addMappings(new PropertyMap<DictPo, DictDetailVO>() {

            @Override
            protected void configure() {
                map().setDictLabel(source.getDictValue());
            }
        });
        modelMapper.map(dictPo,dictDetailVO);
        System.out.println(dictDetailVO);
    }
}
