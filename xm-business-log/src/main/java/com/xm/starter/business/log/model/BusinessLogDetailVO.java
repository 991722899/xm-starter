package com.xm.starter.business.log.model;

import lombok.Data;

import java.util.List;

@Data
public class BusinessLogDetailVO extends BusinessDetailLogPO {
    private List<BusinessDetailLogListVo> details;
}
