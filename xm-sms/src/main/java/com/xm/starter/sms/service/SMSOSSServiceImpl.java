package com.xm.starter.sms.service;

import cn.hutool.core.bean.BeanUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xm.starter.base.model.PageVO;
import com.xm.starter.mybatis.model.MyBatisPlusPage;
import com.xm.starter.base.util.Assert;
import com.xm.starter.sms.enums.SmsSendStatus;
import com.xm.starter.sms.enums.SmsVendor;
import com.xm.starter.sms.mapper.SMSMapper;
import com.xm.starter.sms.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SMSOSSServiceImpl implements SMSService {
    private  SMSMapper smsMapper;
    private Client client;
    private ObjectMapper objectMapper;

    public SMSOSSServiceImpl(SMSMapper smsMapper, Client client,ObjectMapper objectMapper) {
        this.smsMapper = smsMapper;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    @Override
    public PageVO<SMSListVO> page(QuerySMSDTO query) {
        return smsMapper.page(new MyBatisPlusPage<>(query.getPageNum(),query.getPageSize()),query).toPageVO();
    }

    @Override
    public List<SMSListVO> list(QuerySMSDTO query) {
        return smsMapper.list(query);
    }

    @Override
    public String insert(SMSInsertDTO insertDTO) {
        SMSPO smspo = new SMSPO();
        BeanUtils.copyProperties(insertDTO,smspo);
        Assert.isTrue(smsMapper.insert(smspo)>0,"新增失败");
        return smspo.getId().toString();
    }

    @Override
    public String updateById(SMSUpdateByIdDTO updateByIdDTO) {
        SMSPO smspo = new SMSPO();
        BeanUtils.copyProperties(updateByIdDTO,smspo);
        Assert.isTrue(smsMapper.updateById(smspo)>0,"更新失败");
        return smspo.getId().toString();
    }

    @Override
    public void deleteById(List<Long> id) {
        Assert.isTrue(smsMapper.deleteByIds(id)==id.size(),"删除失败");
    }

    @Override
    public SMSDetailVO findById(Long id) {
        SMSPO smspo = smsMapper.selectById(id);
        SMSDetailVO smsDetailVO = new SMSDetailVO();
        BeanUtil.copyProperties(smspo,smsDetailVO);
        return smsDetailVO;
    }

    @Override
    public void send(SMSSendDTO sendDTO) throws Exception {
        SMSPO smspo = new SMSPO();
        smspo.setTemplateParam(objectMapper.writeValueAsString(sendDTO.getTemplateParam()));
        smspo.setPhone(sendDTO.getPhone());
        smspo.setTemplateCode(sendDTO.getTemplateCode());
        smspo.setStatus(SmsSendStatus.PENDING.getCode());
        smspo.setVendorType(SmsVendor.ALIYUN.getVendorName());
        smsMapper.insert(smspo);

        SendSmsRequest smsRequest = new SendSmsRequest();
        smsRequest.setPhoneNumbers(sendDTO.getPhone());
        smsRequest.setSignName(sendDTO.getSignName());
        smsRequest.setTemplateCode(sendDTO.getTemplateCode());
        smsRequest.setTemplateParam(objectMapper.writeValueAsString(sendDTO.getTemplateParam()));
        smsRequest.setOutId(smspo.getId().toString());
        try {
            SendSmsResponse sendSmsResponse = client.sendSms(smsRequest);
            if(sendSmsResponse.getBody().getCode().equals("OK")){
                smsMapper.update(new LambdaUpdateWrapper<SMSPO>().eq(SMSPO::getId,smspo.getId())
                        .set(SMSPO::getStatus,SmsSendStatus.SUCCESS.getCode())
                        .set(SMSPO::getRequestId,sendSmsResponse.getBody().getRequestId())
                        .set(SMSPO::getBizId,sendSmsResponse.getBody().getBizId())
                        .set(SMSPO::getSendTime, LocalDateTime.now()));
            }else{
                smsMapper.update(new LambdaUpdateWrapper<SMSPO>().eq(SMSPO::getId,smspo.getId())
                        .set(SMSPO::getStatus,SmsSendStatus.FAILURE.getCode())
                        .set(SMSPO::getRequestId,sendSmsResponse.getBody().getRequestId())
                        .set(SMSPO::getBizId,sendSmsResponse.getBody().getBizId())
                        .set(SMSPO::getSendTime, LocalDateTime.now())
                        .set(SMSPO::getExceptionMsg, sendSmsResponse.getBody().getMessage()));
                Assert.isTrue(sendSmsResponse.getBody().getCode().equals("OK"),sendSmsResponse.getBody().getMessage());
            }
        }catch (Exception e){
            smsMapper.update(new LambdaUpdateWrapper<SMSPO>().eq(SMSPO::getId,smspo.getId())
                    .set(SMSPO::getStatus,SmsSendStatus.FAILURE.getCode())
                    .set(SMSPO::getExceptionMsg,e.getMessage()));
            throw e;
        }
    }
}
