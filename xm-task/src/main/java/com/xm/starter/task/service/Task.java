package com.xm.starter.task.service;

import com.xm.starter.task.model.dto.TaskDTO;
import com.xm.starter.task.model.TaskEven;

public interface Task<T>{
    TaskEven execution(TaskDTO<T> t);
}
