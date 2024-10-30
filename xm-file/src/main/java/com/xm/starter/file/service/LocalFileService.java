package com.xm.starter.file.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.xm.starter.file.mapper.FileMapper;
import com.xm.starter.file.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Primary
@Service
public class LocalFileService extends AbsFileService{
    private @Autowired FileProperties fileProperties;
    private @Autowired FileMapper fileMapper;

    @Override
    public byte[] download(Long id) throws IOException {
        FilePO filePO = fileMapper.selectById(id);
        return IoUtil.readBytes(new FileInputStream(fileProperties.getLocalFileConfig().getRootPath().concat(filePO.getPath())), true);
    }

    @Override
    public void deleteById(List<Long> id) {
        List<FilePO> filePOList = fileMapper.selectBatchIds(id);
        for (FilePO filePO : filePOList) {
            File file = new File(fileProperties.getLocalFileConfig().getRootPath().concat(filePO.getPath()));
            if(file.exists()){
                file.delete();
            }
        }
        fileMapper.deleteByIds(id);
    }

    @Override
    public List<FileListVO> upload(List<FileUploadDTO> fileUploadDTOList) {
        List<FilePO> filePOList = new ArrayList<>();
        for (FileUploadDTO fileUploadDTO : fileUploadDTOList) {
            String targetFilePath = createPath(fileProperties.getLocalFileConfig().getRootPath(),fileUploadDTO.getBusinessType(),fileUploadDTO.getBusinessNo(),fileUploadDTO.getFile().getName());
            File targetFile = FileUtil.touch(targetFilePath);
            FileUtil.copy(fileUploadDTO.getFile(),targetFile,true);
            FilePO filePO = fileUploadDTO.toFilePO();
            filePO.setContentType(FileUtil.getMimeType(fileUploadDTO.getFile().getName()));
            filePO.setLength(fileUploadDTO.getFile().length());
            filePO.setPath(targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),""));
            filePO.setAccessUrl(fileProperties.getLocalFileConfig().getDomain()+targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),"")
                    .replace(File.separator,"/"));
            filePOList.add(filePO);
        }
        fileMapper.insert(filePOList);
        return fileMapper.list(QueryFileDTO.builder().id(filePOList.stream().map(FilePO::getId).collect(Collectors.toList())).build());
    }

    @Override
    public List<FileListVO> uploadByte(List<FileByteUploadDTO> fileByteUploadDTOList) throws FileNotFoundException {
        List<FilePO> filePOList = new ArrayList<>();
        for (FileByteUploadDTO fileByteUploadDTO : fileByteUploadDTOList) {
            String targetFilePath = createPath(fileProperties.getLocalFileConfig().getRootPath(),fileByteUploadDTO.getBusinessType(),fileByteUploadDTO.getBusinessNo(),fileByteUploadDTO.getOriginalName());
            File targetFile = FileUtil.touch(targetFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            IoUtil.write(fileOutputStream,true, fileByteUploadDTO.getBytes());
            FilePO filePO = fileByteUploadDTO.toFilePO();
            filePO.setContentType(FileUtil.getMimeType(targetFile.getPath()));
            filePO.setLength((long)fileByteUploadDTO.getBytes().length);
            filePO.setPath(targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),""));
            filePO.setAccessUrl(fileProperties.getLocalFileConfig().getDomain()+targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),"")
                    .replace(File.separator,"/"));
            filePOList.add(filePO);
        }
        fileMapper.insert(filePOList);
        return fileMapper.list(QueryFileDTO.builder().id(filePOList.stream().map(FilePO::getId).collect(Collectors.toList())).build());
    }

    @Override
    public List<FileListVO> uploadInputStream(List<FileInputStreamUploadDTO> fileInputStreamUploadDTOList) throws FileNotFoundException {
        List<FilePO> filePOList = new ArrayList<>();
        for (FileInputStreamUploadDTO fileInputStreamUploadDTO : fileInputStreamUploadDTOList) {
            String targetFilePath = createPath(fileProperties.getLocalFileConfig().getRootPath(),fileInputStreamUploadDTO.getBusinessType(),fileInputStreamUploadDTO.getBusinessNo(),fileInputStreamUploadDTO.getOriginalName());
            File targetFile = FileUtil.touch(targetFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            long length = IoUtil.copy(fileInputStreamUploadDTO.getInputStream(),fileOutputStream,2048);
            FilePO filePO = fileInputStreamUploadDTO.toFilePO();
            filePO.setContentType(FileUtil.getMimeType(targetFile.getPath()));
            filePO.setLength(length);
            filePO.setPath(targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),""));
            filePO.setAccessUrl(fileProperties.getLocalFileConfig().getDomain()+targetFilePath.replace(fileProperties.getLocalFileConfig().getRootPath(),"")
                    .replace(File.separator,"/"));
            filePOList.add(filePO);
        }
        fileMapper.insert(filePOList);
        return fileMapper.list(QueryFileDTO.builder().id(filePOList.stream().map(FilePO::getId).collect(Collectors.toList())).build());
    }

    @Override
    public List<FileListVO> copy(List<Long> id) {
        return Collections.emptyList();
    }

    @Override
    public List<FileListVO> copy(String batchNo) {
        return Collections.emptyList();
    }
}
