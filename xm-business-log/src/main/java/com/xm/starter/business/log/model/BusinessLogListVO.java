package com.xm.starter.business.log.model;

import lombok.Data;

import java.util.List;

@Data
public class BusinessLogListVO extends BusinessLogPo {
    private List<BusinessDetailLogListVo> details;
}
