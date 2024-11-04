import cn.hutool.core.collection.ListUtil;
import com.xm.starter.business.log.model.*;
import com.xm.starter.business.log.service.BusinessLogDefaultHandler;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
@Component
public class BusinessLogHandlerTest extends BusinessLogDefaultHandler<List<Long>, BusinessLogDetailVO> {
    @Override
    public List<BusinessLogDetailVO> before(List<Long> p) {
        BusinessLogDetailVO update = new BusinessLogDetailVO();
        update.setId(2L);
        update.setName("修改");
        update.setEnName("update");

        BusinessDetailLogListVo updateSub1 = new BusinessDetailLogListVo();
        updateSub1.setId(22L);
        updateSub1.setName("修改SUB1");
        updateSub1.setEnName("update SUB");

        BusinessDetailLogListVo updateSub2 = new BusinessDetailLogListVo();
        updateSub2.setId(222L);
        updateSub2.setName("修改SUB2");
        updateSub2.setEnName("update SUB");

        updateSub1.setChildren(ListUtil.toList(updateSub2));

        update.setDetails(ListUtil.toList(updateSub1));


        return ListUtil.toList(update);
    }

    @Override
    public List<BusinessLogDetailVO> after(List<Long> p) {
        BusinessLogDetailVO update = new BusinessLogDetailVO();
        update.setId(2L);
        update.setName("修改");
        update.setEnName("update");

        BusinessDetailLogListVo updateSub1 = new BusinessDetailLogListVo();
        updateSub1.setId(22L);
        updateSub1.setName("修改SUB1");
        updateSub1.setEnName("update SUB");

        BusinessDetailLogListVo updateSub2 = new BusinessDetailLogListVo();
        updateSub2.setId(222L);
        updateSub2.setName("修改SUB2 22");
        updateSub2.setEnName("update SUB");

        updateSub1.setChildren(ListUtil.toList(updateSub2));

        update.setDetails(ListUtil.toList(updateSub1));


        return ListUtil.toList(update);
    }


    @BusinessLog(value = BusinessLogHandlerTest.class,paramsScript = "return [1,2,3,4];")
    public void test(MockMvc mockMvc, BusinessLogInsertDto businessLogInsertDto,BusinessLogDetailVO businessLogDetailVO){

    }

    @Override
    public List<BusinessLogItemConfig> config() {
        return ListUtil.toList(
                new BusinessLogItemConfig("主键", "id",null, ListUtil.toList(ItemPrimaryKeyType.BUSINESS_ID)),
                new BusinessLogItemConfig("名称", "name"),
                new BusinessLogItemConfig("英文名", "enName"),
                new BusinessLogItemConfig("子项", "details", ListUtil.toList(
                        new BusinessLogItemConfig("子项主键", "id",null, ListUtil.toList(ItemPrimaryKeyType.BUSINESS_ID)),
                        new BusinessLogItemConfig("子项名称", "name"),
                        new BusinessLogItemConfig("子项英文名", "enName"),
                        new BusinessLogItemConfig("子子项", "children", ListUtil.toList(
                                new BusinessLogItemConfig("子项主键", "id",null, ListUtil.toList(ItemPrimaryKeyType.BUSINESS_ID)),
                                new BusinessLogItemConfig("子项名称", "name"),
                                new BusinessLogItemConfig("子项英文名", "enName")
                        ))
                ))
        );
    }
}


