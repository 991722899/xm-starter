package com.xm.starter.log.visual.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.xm.starter.base.util.Assert;
import com.xm.starter.log.visual.model.LogVisualReadVO;
import com.xm.starter.log.visual.model.LogVisualProperties;
import com.xm.starter.log.visual.model.LogVisualReadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class LogVisualService {
    private static final Logger log = LoggerFactory.getLogger(LogVisualService.class);
    private @Autowired LogVisualProperties logVisualProperties;
    /**
     * 日志读取
     * @param query
     * @return
     */
    public LogVisualReadVO read(LogVisualReadDTO query) throws IOException {
        LogVisualReadVO readVO = new LogVisualReadVO();
        if(StrUtil.isBlank(query.getPath())){
            readVO.setFileInfos(logVisualProperties.getPath().stream().map(j->{
                LogVisualReadVO.FileInfo fileInfo = new LogVisualReadVO.FileInfo();
                fileInfo.setName(j);
                fileInfo.setPath(j);
                return fileInfo;
            }).collect(Collectors.toList()));
        }else{
            if(FileUtil.isDirectory(query.getPath())){
                readVO.setFileInfos(FileUtil.listFileNames(query.getPath()).stream().map(j->{
                    Assert.isTrue(FileUtil.exist(query.getPath()),StrUtil.format("文件目录不存在:{}", query.getPath()));
                    LogVisualReadVO.FileInfo fileInfo = new LogVisualReadVO.FileInfo();
                    fileInfo.setName(j);
                    fileInfo.setPath(StrUtil.join(File.separator,query.getPath(),j));
                    return fileInfo;
                }).collect(Collectors.toList()));
            }else{
                Assert.isTrue(logVisualProperties.getPath().stream().anyMatch(j->query.getPath().startsWith(j)),StrUtil.format("访问目录不存在：{}",query.getPath()));
                
                try (RandomAccessFile raf = new RandomAccessFile(query.getPath(), "r")) {
                    raf.seek(query.getOffset()); // 将文件指针移动到指定位置
                    int len = query.getLen()!=null?query.getLen().intValue():logVisualProperties.getLen().intValue();
                    len = len>logVisualProperties.getMaxLen()?logVisualProperties.getMaxLen().intValue():len;
                    byte[] buffer = new byte[len];
                    int bytesRead = raf.read(buffer);
                    readVO.setLogContent(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));
                    readVO.setSize(bytesRead);
                    readVO.setOffset(query.getOffset()+bytesRead);
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return readVO;
    }
}
