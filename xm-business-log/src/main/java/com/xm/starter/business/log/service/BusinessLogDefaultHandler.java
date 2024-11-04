package com.xm.starter.business.log.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.xm.starter.business.log.model.BusinessLog;
import com.xm.starter.business.log.model.BusinessLogInsertDto;
import com.xm.starter.business.log.model.BusinessLogItemConfig;

import java.util.List;

public abstract class BusinessLogDefaultHandler<P,R> implements BusinessLogHandler<P,R> {
    @Override
    public List<BusinessLogItemConfig> config() {
        return ListUtil.toList();
    }

    /**
    * @description：aop代理
    * @author：陈超超
    * @time：2024/11/1 14:07
    */
    @Override
    public void outPut(List<R> before, List<R> after, BusinessLog businessLog) {
        if(CollUtil.isNotEmpty(before) || CollUtil.isNotEmpty(after)){

//            BusinessLogInsertDto businessLogInsertDTO = new BusinessLogInsertDto();
//            businessLogInsertDTO.setBusinessId("");
//            businessLogInsertDTO.setBusinessNol("");
        }

    }

    /**
    * @description：手动添加
    * @author：陈超超
    * @time：2024/11/1 14:07
    */
    public void outPut(BusinessLogInsertDto businessLogInsertDTO, List<Object> before, List<Object> after){

    }

    private void parse(List<Object> before, List<Object> after){

    }
}
