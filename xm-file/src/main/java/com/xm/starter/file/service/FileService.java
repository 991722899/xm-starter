package com.xm.starter.file.service;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.file.enums.StorageType;
import com.xm.starter.file.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    byte[] download(Long id) throws IOException;
    void deleteById(List<Long> id);
    void deleteByBatchNo(List<String> batchNo);
    List<FileListVO> upload(StorageType storageType, String businessType, String businessNo, Long businessId, MultipartFile... file) throws IOException;
    List<FileListVO> upload(List<FileUploadDTO> fileUploadDTOList);
    List<FileListVO> uploadByte(List<FileByteUploadDTO> fileByteUploadDTOList) throws FileNotFoundException;
    List<FileListVO> uploadInputStream(List<FileInputStreamUploadDTO> fileInputStreamUploadDTOList) throws FileNotFoundException;
    FileDetailVO findById(Long id);
    List<FileListVO> list(QueryFileDTO query);
    PageVO<FileListVO> page(QueryFileDTO query);
    String createBatchNo(List<Long> id,String batchNo);
    String createBatchNo(List<FileDTO> fileCreateBatchNoDTOList);
    List<FileListVO> copy(List<Long> id);
    List<FileListVO> copy(String batchNo);

}
