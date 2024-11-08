import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.slow.sql.config.MybatisSlowSqlInterceptor;
import com.xm.starter.slow.sql.config.SlowSqlConfiguration;
import com.xm.starter.slow.sql.mapper.SlowSqlMapper;
import com.xm.starter.slow.sql.model.QuerySlowSql;
import com.xm.starter.slow.sql.model.SlowSqlPO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = {SlowSqlConfiguration.class, MybatisSlowSqlInterceptor.class})
public class SlowSqlTest {
    private @Autowired SlowSqlMapper slowSqlMapper;

    @Test
    public void test() throws SQLException {

//        System.out.println(slowSqlMapper.selectList(new LambdaQueryWrapper<SlowSqlPO>()
//                .eq(SlowSqlPO::getSqlText,"select * from sys_user")
//                .eq(SlowSqlPO::getCreateId,"1")));

        slowSqlMapper.page(new MyBatisPlusPage(1L,10L),new QuerySlowSql());
    }
}
