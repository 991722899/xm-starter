import cn.hutool.core.collection.ListUtil;
import com.xm.starter.business.log.model.BusinessDetailLogPO;
import com.xm.starter.business.log.model.BusinessLog;
import com.xm.starter.business.log.model.BusinessLogDetailVO;
import com.xm.starter.business.log.model.BusinessLogInsertDto;
import com.xm.starter.business.log.service.BusinessLogDefaultHandler;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
@Component
public class BusinessLogHandlerTest extends BusinessLogDefaultHandler<List<Long>, BusinessLogDetailVO> {
    @Override
    public List<BusinessLogDetailVO> before(List<Long> p) {
        return ListUtil.toList();
    }

    @Override
    public List<BusinessLogDetailVO> after(List<Long> p) {
        return ListUtil.toList();
    }


    @BusinessLog(value = BusinessLogHandlerTest.class,paramsScript = "return {abced:'456 789    ',def:'7 98 '};")
    public void test(MockMvc mockMvc, BusinessLogInsertDto businessLogInsertDto,BusinessLogDetailVO businessLogDetailVO){

    }
}


