package com.xm.starter.file.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.file.enums.StorageType;
import com.xm.starter.file.model.FileDetailVO;
import com.xm.starter.file.model.FileListVO;
import com.xm.starter.file.model.QueryFileDTO;
import com.xm.starter.file.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Tag(name = "file", description = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {
    private @Autowired FileService fileService;

    @RequestMapping("/upload")
    public List<FileListVO> upload(StorageType storageType, String businessType, String businessNo, Long businessId, @RequestParam("file")MultipartFile ... file) throws IOException {
        return fileService.upload(storageType,businessType,businessNo,businessId,file);
    }

    @RequestMapping("/page")
    public PageVO<FileListVO> page(@RequestBody QueryFileDTO query) {
        return fileService.page(query);
    }

    @RequestMapping("/list")
    public List<FileListVO> list(@RequestBody QueryFileDTO query) {
        return fileService.list(query);
    }


    @RequestMapping("/findById")
    public FileDetailVO findById(Long id){
        return fileService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        fileService.deleteById(id);
    }
}
