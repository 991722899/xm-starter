package com.xm.starter.area.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Data;

import java.util.List;

@Data
public class QueryArealDTO extends QueryPage {
    private Long pcode;
    private List<Integer> level;
}
