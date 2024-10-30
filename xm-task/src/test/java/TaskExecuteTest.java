import com.xm.starter.mybatis.model.BasePO;
import com.xm.starter.task.enums.TaskStatus;
import com.xm.starter.task.model.TaskEven;
import com.xm.starter.task.model.dto.TaskDTO;
import com.xm.starter.task.service.Task;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskExecuteTest implements Task<BasePO> {

    @Override
    public TaskEven execution(TaskDTO<BasePO> t) {
        TaskEven taskEven = new TaskEven();
        taskEven.setTaskId(t.getTask().getId());
        taskEven.setStatus(TaskStatus.SUCCESS.getCode());
        taskEven.setCreateTime(LocalDateTime.now());
        return taskEven;
    }
}
