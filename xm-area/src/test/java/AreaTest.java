import cn.hutool.core.collection.ListUtil;
import com.xm.starter.area.config.AreaConfiguration;
import com.xm.starter.area.model.AreaListVO;
import com.xm.starter.area.model.QueryArealDTO;
import com.xm.starter.area.service.AreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = AreaConfiguration.class)
public class AreaTest {
    private @Autowired AreaService areaService;
    @Test
    public void list(){
        QueryArealDTO queryArealDTO = new QueryArealDTO();
        queryArealDTO.setPcode(0L);
        List<AreaListVO> areaListVOList = areaService.list(queryArealDTO);
        areaListVOList.forEach(item-> System.out.println(item.getName()));
        queryArealDTO.setPcode(null);
        queryArealDTO.setLevel(ListUtil.toList(1,2,3));
        areaListVOList = areaService.list(queryArealDTO);
        areaListVOList.forEach(item-> System.out.println(item.getName()));



    }
}
