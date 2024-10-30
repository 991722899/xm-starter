package com.xm.starter.area.model;

import lombok.Data;

import java.util.List;

@Data
public class AreaListVO extends AreaPO {
    private List<AreaListVO> children;
}
