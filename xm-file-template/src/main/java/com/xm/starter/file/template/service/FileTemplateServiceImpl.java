package com.xm.starter.file.template.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xm.starter.base.exception.BaseException;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.file.model.FileListVO;
import com.xm.starter.file.model.QueryFileDTO;
import com.xm.starter.file.service.FileService;
import com.xm.starter.file.template.mapper.FileTemplateMapper;
import com.xm.starter.file.template.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FileTemplateServiceImpl implements FileTemplateService{
    private @Autowired FileTemplateMapper fileTemplateMapper;
    private @Autowired FileService fileService;

    @Override
    public byte[] download(String templateKey) throws BaseException, IOException {
        FileTemplatePO fileTemplatePO = fileTemplateMapper.selectOne(new LambdaUpdateWrapper<FileTemplatePO>().eq(FileTemplatePO::getTemplateKey,templateKey));
        Assert.isTrue(fileTemplatePO!=null, StrUtil.format("模板KEY：{}，对应的模板不存在",templateKey));
        List<FileListVO> fileListVOList = fileService.list(QueryFileDTO.builder().batchNo(fileTemplatePO.getFileBatchNo()).build());
        Assert.isTrue(CollUtil.isNotEmpty(fileListVOList),"模板KEY:{},对应的模板文件不存在");
        return fileService.download(fileListVOList.get(0).getId());
    }

    @Override
    public void deleteById(List<Long> id) {
        List<FileTemplatePO> fileTemplatePOList = fileTemplateMapper.selectBatchIds(id);
        for (FileTemplatePO fileTemplatePO : fileTemplatePOList) {
            fileService.deleteByBatchNo(ListUtil.toList(fileTemplatePO.getFileBatchNo()));
            fileTemplateMapper.deleteById(fileTemplatePO.getId());
        }
    }

    @Override
    public String insert(FileTemplateInsertDTO insertDTO) {
        FileTemplatePO fileTemplatePO = new FileTemplatePO();
        BeanUtils.copyProperties(insertDTO,fileTemplatePO);
        fileTemplatePO.setFileBatchNo(fileService.createBatchNo(insertDTO.getFileDTOList()));
        fileTemplateMapper.insert(fileTemplatePO);
        return fileTemplatePO.getId().toString();
    }

    @Override
    public String updateById(FileTemplateUpdateByIdDTO updateByIdDTO) {
        FileTemplatePO fileTemplatePO = new FileTemplatePO();
        BeanUtils.copyProperties(updateByIdDTO,fileTemplatePO);
        fileTemplatePO.setFileBatchNo(fileService.createBatchNo(updateByIdDTO.getFileDTOList()));
        fileTemplateMapper.updateById(fileTemplatePO);
        return fileTemplatePO.getId().toString();
    }

    @Override
    public PageVO<FileTemplateListVO> page(QueryFileTemplateDTO query) {
        return fileTemplateMapper.page(new MyBatisPlusPage<FileTemplateListVO>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<FileTemplateListVO> list(QueryFileTemplateDTO query) {
        return fileTemplateMapper.list(query);
    }

    @Override
    public FileTemplateDetailVO findById(Long id) {
       FileTemplatePO fileTemplatePO = fileTemplateMapper.selectById(id);
       FileTemplateDetailVO detailVO = new FileTemplateDetailVO();
        BeanUtils.copyProperties(fileTemplatePO,detailVO);
       return detailVO;
    }
}
