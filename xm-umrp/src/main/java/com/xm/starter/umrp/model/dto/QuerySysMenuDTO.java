package com.xm.starter.umrp.model.dto;

import com.xm.starter.base.query.QueryPage;
import lombok.Data;

import java.util.List;

@Data
public class QuerySysMenuDTO extends QueryPage {
    private List<Long> id;
}
