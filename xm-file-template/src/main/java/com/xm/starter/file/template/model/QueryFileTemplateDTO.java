package com.xm.starter.file.template.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QueryFileTemplateDTO extends QueryPage {
    private String templateKey;
    private List<Long> id;
}
