package com.xm.starter.variable.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QueryVariableDTO extends QueryPage {
    private String title;
    private String variableKey;
    private List<Long> id;
}
