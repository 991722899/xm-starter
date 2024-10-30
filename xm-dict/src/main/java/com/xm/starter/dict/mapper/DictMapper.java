package com.xm.starter.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xm.starter.dict.model.DictListVO;
import com.xm.starter.dict.model.DictPo;
import com.xm.starter.dict.model.QueryDictDTO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictMapper extends BaseMapper<DictPo> {
    List<DictListVO> list(@Param("query") QueryDictDTO query);

    MyBatisPlusPage<DictListVO> page(MyBatisPlusPage<DictListVO> page, @Param("query") QueryDictDTO query);
}
