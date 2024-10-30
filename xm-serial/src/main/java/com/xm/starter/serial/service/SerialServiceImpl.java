package com.xm.starter.serial.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.serial.mapper.SerialMapper;
import com.xm.starter.serial.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SerialServiceImpl implements SerialService{
    private final String CURRENT_VALUE_NAME = "current_value";
    private @Autowired SerialMapper serialMapper;

    private Map<String, String> context() {
        Map<String,String> context = new HashMap<>();
        context.put("yy", LocalDateTimeUtil.format(LocalDateTime.now(), "yy"));
        context.put("MM", LocalDateTimeUtil.format(LocalDateTime.now(), "MM"));
        context.put("yyyy", LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy"));
        context.put("yyyyMM",LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMM"));
        context.put("yyyMM",LocalDateTimeUtil.format(LocalDateTime.now(), "yyyMM"));
        context.put("yyMMdd",LocalDateTimeUtil.format(LocalDateTime.now(), "yyMMdd"));
        context.put("yyyyMMdd",LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMdd"));
        return context;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String generateCode(String serialKey) {
        Assert.isTrue(serialMapper.next(serialKey)>0,"序列号获取失败");
        SerialPO serial = serialMapper.selectOne(new LambdaQueryWrapper<SerialPO>().eq(SerialPO::getSerialKey,serialKey));

        Map context = context();
        context.put(CURRENT_VALUE_NAME,"");
        String value = StrUtil.format(serial.getTemplate(),context);

        //重置序列号计数器
        if(serial.getTemplateValue()==null || !serial.getTemplateValue().equals(value)){
            SerialPO serialUpdate = new SerialPO();
            serialUpdate.setId(serial.getId());
            serialUpdate.setCurrentValue(serial.getMinValue());
            Assert.isTrue(serialMapper.next(serialKey)>0,"序列号生成失败");
            serial = serialMapper.selectOne(new LambdaQueryWrapper<SerialPO>().eq(SerialPO::getSerialKey,serialUpdate));
        }
        //更新模板值
        serial.setTemplateValue(value);
        Assert.isTrue(serialMapper.updateById(serial)>0,"序列号生成失败");


        context.put(CURRENT_VALUE_NAME,StrUtil.padPre(serial.getCurrentValue().toString(),serial.getMaxValue().toString().length(),"0"));
        return StrUtil.format(serial.getTemplate(),context);
    }

    @Override
    public PageVO<SerialListVO> page(QuerySerialDTO query) {
        return serialMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<SerialListVO> list(QuerySerialDTO query) {
        return serialMapper.list(query);
    }

    @Override
    public String insert(SerialInsertDTO insertDTO) {
        SerialPO serialPO = new SerialPO();
        BeanUtils.copyProperties(insertDTO,serialPO);
        Assert.isTrue(serialMapper.insert(serialPO)>0,"新增失败");
        return serialPO.getId().toString();
    }

    @Override
    public String updateById(SerialUpdateByIdDTO updateByIdDTO) {
        SerialPO serialPO = new SerialPO();
        BeanUtils.copyProperties(updateByIdDTO,serialPO);
        Assert.isTrue(serialMapper.updateById(serialPO)>0,"更新失败");
        return serialPO.getId().toString();
    }

    @Override
    public void deleteById(List<Long> id) {
        Assert.isTrue(serialMapper.deleteByIds(id)==id.size(),"删除失败");
    }

    @Override
    public SerialDetailVO findById(Long id) {
        SerialPO serialPO = serialMapper.selectById(id);
        SerialDetailVO serialDetailVO = SerialDetailVO.builder().build();
        BeanUtil.copyProperties(serialPO,serialDetailVO);
        return serialDetailVO;
    }
}
