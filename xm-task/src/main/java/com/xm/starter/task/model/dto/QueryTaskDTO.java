package com.xm.starter.task.model.dto;

import com.xm.starter.base.query.QueryPage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class QueryTaskDTO extends QueryPage {

    /**
     * 主键
     */
    @Schema(description = "主键")
    private List<Long> id;
}
