package com.xm.starter.business.log.service;

import com.xm.starter.business.log.model.BusinessLog;
import com.xm.starter.business.log.model.BusinessLogItemConfig;

import java.util.List;
public interface BusinessLogHandler<P,R> {
    List<R> before(P p);
    List<R> after(P p);
    List<BusinessLogItemConfig> config();
    void outPut(List<R> before, List<R> after, BusinessLog businessLog) throws Exception;
}
