package com.xm.starter.file.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.xm.starter.file.mapper.FileMapper;
import com.xm.starter.file.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OssFileService extends AbsFileService{
    private @Autowired FileMapper fileMapper;
    private @Autowired FileProperties fileProperties;

    @Override
    public byte[] download(Long id){
        FilePO filePO = fileMapper.selectById(id);
        return download(filePO.getPath());
    }


    @Override
    public void deleteById(List<Long> id) {
        List<FilePO> filePOList = fileMapper.selectBatchIds(id);
        for (FilePO filePO : filePOList) {
           delete(filePO.getPath());
        }
        fileMapper.deleteByIds(id);
    }

    @Override
    public List<FileListVO> upload(List<FileUploadDTO> fileUploadDTOList) {
        List<FilePO> filePOList = new ArrayList<>();
        for (FileUploadDTO fileUploadDTO : fileUploadDTOList) {
            String filePath = createPath(null,fileUploadDTO.getBusinessType(),fileUploadDTO.getBusinessNo(),fileUploadDTO.getFile().getName());
            FilePO filePO = fileUploadDTO.toFilePO();
            filePO.setContentType(FileUtil.getMimeType(fileUploadDTO.getFile().getName()));
            filePO.setLength(fileUploadDTO.getFile().length());
            filePO.setPath(filePath);
            //上传到阿里云
            upload(filePath,fileUploadDTO.getFile());
            filePO.setAccessUrl(createUrl(filePath));
            filePOList.add(filePO);
        }
        fileMapper.insert(filePOList);
        return fileMapper.list(QueryFileDTO.builder().id(filePOList.stream().map(FilePO::getId).collect(Collectors.toList())).build());
    }

    @Override
    public List<FileListVO> uploadByte(List<FileByteUploadDTO> fileByteUploadDTOList) throws FileNotFoundException {
        List<FilePO> filePOList = new ArrayList<>();
        for (FileByteUploadDTO fileByteUploadDTO : fileByteUploadDTOList) {
            String filePath = createPath(null,fileByteUploadDTO.getBusinessType(),fileByteUploadDTO.getBusinessNo(),fileByteUploadDTO.getOriginalName());
            FilePO filePO = fileByteUploadDTO.toFilePO();
            filePO.setContentType(fileByteUploadDTO.getContentType());
            filePO.setLength((long)fileByteUploadDTO.getBytes().length);
            filePO.setPath(filePath);
            //上传到阿里云
            upload(filePath,new ByteArrayInputStream(fileByteUploadDTO.getBytes()));
            filePO.setAccessUrl(createUrl(filePath));
            filePOList.add(filePO);
        }
        fileMapper.insert(filePOList);
        return fileMapper.list(QueryFileDTO.builder().id(filePOList.stream().map(FilePO::getId).collect(Collectors.toList())).build());
    }

    @Override
    public List<FileListVO> uploadInputStream(List<FileInputStreamUploadDTO> fileInputStreamUploadDTOList){
        List<FilePO> filePOList = new ArrayList<>();
        for (FileInputStreamUploadDTO fileInputStreamUploadDTO : fileInputStreamUploadDTOList) {
            String filePath = createPath(null,fileInputStreamUploadDTO.getBusinessType(),fileInputStreamUploadDTO.getBusinessNo(),fileInputStreamUploadDTO.getOriginalName());
            FilePO filePO = fileInputStreamUploadDTO.toFilePO();
            filePO.setContentType(fileInputStreamUploadDTO.getContentType());
            filePO.setPath(filePath);
            //上传到阿里云
            PutObjectResult putObjectResult = upload(filePath,fileInputStreamUploadDTO.getInputStream());
            filePO.setLength(putObjectResult.getResponse().getContentLength());
            filePO.setAccessUrl(createUrl(filePath));
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

    private OSS createOssClient(){
        return new OSSClientBuilder().build(fileProperties.getOssFileConfig().getEndpoint(), fileProperties.getOssFileConfig().getAccessKeyId(),fileProperties.getOssFileConfig().getAccessKeySecret());
    }


    private PutObjectResult upload(String objectName, File file){
        OSS oss = null;
        try {
            oss = createOssClient();
            return oss.putObject(fileProperties.getOssFileConfig().getBucketName(),objectName,file);
        }catch (OSSException ossException){
            throw ossException;
        }finally {
            if(oss!=null){
                oss.shutdown();
            }
        }
    }

    private PutObjectResult upload(String objectName, InputStream inputStream){
        OSS oss = null;
        try {
            oss = createOssClient();
            return oss.putObject(fileProperties.getOssFileConfig().getBucketName(),objectName,inputStream);
        }catch (OSSException ossException){
            throw ossException;
        }finally {
            if(oss!=null){
                oss.shutdown();
            }
        }
    }

    private String createUrl(String objectName){
        OSS oss = null;
        try {
            oss = createOssClient();
            return oss.generatePresignedUrl(fileProperties.getOssFileConfig().getBucketName(),objectName,new Date(new Date().getTime() +fileProperties.getOssFileConfig().getUrlExpirationMillisecond())).toString();
        }catch (OSSException ossException){
            throw ossException;
        }finally {
            if(oss!=null){
                oss.shutdown();
            }
        }
    }

    private void delete(String objectName){
        OSS oss = null;
        try {
            oss = createOssClient();
            oss.deleteObject(fileProperties.getOssFileConfig().getBucketName(),objectName);
        }catch (OSSException ossException){
            throw ossException;
        }finally {
            if(oss!=null){
                oss.shutdown();
            }
        }
    }

    private byte[] download(String objectName){
        OSS oss = null;
        InputStream inputStream = null;
        try {
            oss = createOssClient();
            inputStream = oss.getObject(fileProperties.getOssFileConfig().getBucketName(),objectName).getObjectContent();
            return IoUtil.readBytes(inputStream,true);
        }catch (OSSException ossException){
            throw ossException;
        }catch (Exception e){
            throw new OSSException(e.getMessage(),e);
        }finally {
            if(oss!=null){
                oss.shutdown();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new OSSException(e.getMessage(),e);
                }
            }
        }
    }
}
