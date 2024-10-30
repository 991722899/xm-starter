package com.xm.starter.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryPage extends Query {
    @Schema(description = "页码")
    private Long pageNum;
    @Schema(description = "每页数量")
    private Long pageSize;
}
