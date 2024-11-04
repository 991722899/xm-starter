package com.xm.starter.business.log.model;

import lombok.Data;

import java.util.List;

@Data
public class BusinessLogDetailVO extends BusinessLogPo {
    private List<BusinessDetailLogListVo> details;
}
