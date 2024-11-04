package com.xm.starter.business.log.model;

import lombok.Data;

import java.util.List;

@Data
public class BusinessDetailLogListVo extends BusinessDetailLogPO {
    private List<BusinessDetailLogListVo> children;
}
