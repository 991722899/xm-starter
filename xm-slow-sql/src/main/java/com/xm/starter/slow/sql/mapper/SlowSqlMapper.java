package com.xm.starter.slow.sql.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.slow.sql.model.QuerySlowSql;
import com.xm.starter.slow.sql.model.SlowSqlPO;
import org.apache.ibatis.annotations.Param;

public interface SlowSqlMapper extends RootMapper<SlowSqlPO> {
    MyBatisPlusPage<SlowSqlPO> page(MyBatisPlusPage<SlowSqlPO> page, @Param("query") QuerySlowSql query);
}
