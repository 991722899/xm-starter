package com.xm.starter.dict.model;

import lombok.Data;

import java.util.List;

@Data
public class DictListVO extends DictPo{
    private List<DictListVO> children;
}
