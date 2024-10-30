package com.xm.starter.mybatis.model;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xm.starter.base.model.PageVO;

public class MyBatisPlusPage<T> extends Page<T> {
    public MyBatisPlusPage() {
        super(1,10);
    }

    public MyBatisPlusPage(Long pageNum, Long pageSize) {
        super(ObjectUtil.defaultIfNull(pageNum,1L), ObjectUtil.defaultIfNull(pageSize,10L));
    }
    public MyBatisPlusPage(Long pageNum, Long pageSize, Long total) {
        super(pageNum, pageSize, total);
    }

    public PageVO<T> toPageVO() {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setData(this.getRecords());
        pageVO.setPageNum(this.getCurrent());
        pageVO.setPageSize(this.getSize());
        pageVO.setTotal(this.getTotal());
        return pageVO;
    }

}
