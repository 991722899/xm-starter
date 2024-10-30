package com.xm.starter.file.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.file.enums.StorageType;
import com.xm.starter.file.mapper.FileMapper;
import com.xm.starter.file.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbsFileService implements FileService{
    private @Autowired FileMapper fileMapper;
    private @Autowired FileProperties fileProperties;
    private @Resource(name = "ossFileService") FileService ossFileService;
    private @Resource(name = "localFileService") FileService localFileService;

    @Override
    public FileDetailVO findById(Long id) {
       FilePO filePO = fileMapper.selectById(id);
       FileDetailVO fileDetailVO = new FileDetailVO();
        BeanUtils.copyProperties(filePO,fileDetailVO);
        return fileDetailVO;
    }

    @Override
    public List<FileListVO> list(QueryFileDTO query) {
        return fileMapper.list(query);
    }

    @Override
    public PageVO<FileListVO> page(QueryFileDTO query) {
       return fileMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public String createBatchNo(List<Long> id, String batchNo) {
        batchNo = ObjectUtil.defaultIfBlank(batchNo,IdUtil.fastSimpleUUID());
        fileMapper.update(null,new LambdaUpdateWrapper<FilePO>().in(FilePO::getId,id)
                .set(FilePO::getTemporaryFile,1)
                .set(FilePO::getBatchNo,batchNo));
        return batchNo;
    }

    protected String createPath(String rootPath,String businessType,String businessNo,String originalName){
        businessType = ObjectUtil.defaultIfBlank(businessType,"default").replaceAll("[\\\\/]","");
        businessNo = ObjectUtil.defaultIfBlank(businessNo,"").replaceAll("[\\\\/]","");
        originalName = ObjectUtil.defaultIfBlank(originalName,"").replaceAll("[\\\\/]","");

        return ListUtil.toList(
                rootPath,
                businessType,
                String.valueOf(LocalDate.now().getYear()),
                String.valueOf(LocalDate.now().getMonthValue()),
                String.valueOf(LocalDate.now().getDayOfMonth()),
                businessNo,
                IdUtil.fastSimpleUUID().concat(StrUtil.isNotBlank(FileUtil.getSuffix(originalName))?"."+FileUtil.getSuffix(originalName):""))
                .stream().filter(StrUtil::isNotBlank)
                .collect(Collectors.joining(File.separator));
    }

    @Override
    public List<FileListVO> upload(StorageType storageType, String businessType, String businessNo, Long businessId, MultipartFile... file) throws IOException {
        List<FileInputStreamUploadDTO> fileInputStreamUploadDTOList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            fileInputStreamUploadDTOList.add(FileInputStreamUploadDTO.builder()
                    .contentType(multipartFile.getContentType())
                    .businessType(businessType)
                    .businessId(businessId)
                    .businessNo(businessNo)
                    .inputStream(multipartFile.getInputStream())
                    .originalName(multipartFile.getOriginalFilename())
                    .build());
        }
        if(storageType==null || storageType==StorageType.LOCAL){
            return localFileService.uploadInputStream(fileInputStreamUploadDTOList);
        }else{
            return ossFileService.uploadInputStream(fileInputStreamUploadDTOList);
        }
    }

    @Override
    public String createBatchNo(List<FileDTO> fileCreateBatchNoDTOList) {
        String batchNo = IdUtil.fastSimpleUUID();
        int deleteSize = 0;
        for (FileDTO fileCreateBatchNoDTO : fileCreateBatchNoDTOList) {
            if(fileCreateBatchNoDTO.getDelete()){
                deleteById(ListUtil.toList(fileCreateBatchNoDTO.getId()));
                deleteSize++;
            }
        }
        return deleteSize == fileCreateBatchNoDTOList.size()?"empty":batchNo;
    }

    @Override
    public void deleteByBatchNo(List<String> batchNo) {
        List<FilePO> filePOList = fileMapper.selectList(new LambdaQueryWrapper<FilePO>().in(FilePO::getBatchNo,batchNo));
        deleteById(filePOList.stream().map(FilePO::getId).collect(Collectors.toList()));
    }
}
