package com.xm.starter.slow.sql.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xm.starter.mybatis.model.BasePO;
import lombok.Data;
@TableName("sys_slow_sql")
@Data
public class SlowSqlPO extends BasePO {
    private String sqlText;
    private String stackInfo;
    private Long timeConsuming;
    private String mapperId;
}
