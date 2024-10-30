package com.xm.starter.task.model.vo;

import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;

@Data
public class TaskInputStreamVO extends TaskVO {
    private String fileName;
    private InputStream inputStream;
    private boolean closeIO = true;
    private OutputStream outputStream;
}
