package com.xm.starter.sms.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QuerySMSDTO extends QueryPage {
    private List<Long> id;
}
