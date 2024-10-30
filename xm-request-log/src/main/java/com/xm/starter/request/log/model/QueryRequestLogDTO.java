package com.xm.starter.request.log.model;

import com.xm.starter.base.query.QueryPage;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class QueryRequestLogDTO extends QueryPage {
    private List<Long> id;
    private String ip;
    private String userId;
    private String userName;

    private LocalDateTime begin_create_time;
    private LocalDateTime end_create_time;
}
