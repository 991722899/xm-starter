package com.xm.starter.sms.controller;

import com.xm.starter.base.model.PageVO;
import com.xm.starter.sms.model.QuerySMSDTO;
import com.xm.starter.sms.model.SMSDetailVO;
import com.xm.starter.sms.model.SMSListVO;
import com.xm.starter.sms.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@ConditionalOnProperty(prefix = "xm.starter.sms",name = "activateController",havingValue = "true")
@RestController
@RequestMapping("/sms")
public class SMSController {
    private @Autowired SMSService smsService;

    @RequestMapping("/page")
    public PageVO<SMSListVO> page(@RequestBody QuerySMSDTO query) {
        return smsService.page(query);
    }

    @RequestMapping("/list")
    public List<SMSListVO> list(@RequestBody QuerySMSDTO query) {
        return smsService.list(query);
    }

    @RequestMapping("/findById")
    public SMSDetailVO findById(Long id){
        return smsService.findById(id);
    }

    @RequestMapping("/deleteById")
    public void deleteById(List<Long> id){
        smsService.deleteById(id);
    }
}
