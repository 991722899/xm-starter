package com.xm.starter.task.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.vo.TaskConfigListVO;
import com.xm.starter.task.model.po.TaskConfigPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskConfigMapper extends RootMapper<TaskConfigPO> {
    List<TaskConfigListVO> list(@Param("query") QueryTaskDTO query);
    MyBatisPlusPage<TaskConfigListVO> page(MyBatisPlusPage<TaskConfigListVO> page, @Param("query") QueryTaskDTO query);
}
