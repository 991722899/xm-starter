package com.xm.starter.task.mapper;

import com.xm.starter.mybatis.mapper.RootMapper;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.task.model.dto.QueryTaskDTO;
import com.xm.starter.task.model.po.TaskPO;
import com.xm.starter.task.model.vo.TaskListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskMapper extends RootMapper<TaskPO> {
    List<TaskListVO> list(@Param("query") QueryTaskDTO query);
    MyBatisPlusPage<TaskListVO> page(MyBatisPlusPage<TaskListVO> page, @Param("query") QueryTaskDTO query);
}
