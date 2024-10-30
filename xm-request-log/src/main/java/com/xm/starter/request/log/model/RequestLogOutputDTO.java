package com.xm.starter.request.log.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RequestLogOutputDTO extends RequestLogPO{
    private List<RequestLogDetailOutputDTO> detailList = new ArrayList<>();
}
