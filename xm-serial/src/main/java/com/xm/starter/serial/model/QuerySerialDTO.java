package com.xm.starter.serial.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class QuerySerialDTO extends QueryPage {
    private String serialKey;
    private List<Long> id;
}
