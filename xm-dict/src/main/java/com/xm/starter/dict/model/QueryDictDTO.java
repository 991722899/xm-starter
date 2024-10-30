package com.xm.starter.dict.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Data;

@Data
public class QueryDictDTO extends QueryPage {
    private String dictValue;
    private String dictLabel;
    private Long parentId;
}
