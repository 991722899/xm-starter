package com.xm.starter.business.log.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.xm.starter.business.log.formatter.BusinessLogItemFormatter;
import com.xm.starter.business.log.mapper.BusinessDetailLogMapper;
import com.xm.starter.business.log.mapper.BusinessLogMapper;
import com.xm.starter.business.log.model.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
@Slf4j
public abstract class BusinessLogDefaultHandler<P,R> implements BusinessLogHandler<P,R> {
    private static final Logger log = LoggerFactory.getLogger(BusinessLogDefaultHandler.class);
    private @Resource(name = "businessLogObjectMapper") ObjectMapper objectMapper;
    private @Autowired BusinessLogMapper businessLogMapper;
    private @Autowired BusinessDetailLogMapper businessDetailLogMapper;

    @Override
    public List<BusinessLogItemConfig> config() {
        return ListUtil.toList();
    }

    private Map<String,JsonNode> jsonNodeMap(JsonNode node, String businessIdKey){
        if(node instanceof ArrayNode){
            Map<String,JsonNode> jsonNodeMap = new HashMap<>();
            for (JsonNode jsonNode : node) {
                if(!jsonNode.has(businessIdKey)){
                    log.info("未配置业务主键，无法输出日志");
                    return null;
                }
                jsonNodeMap.put(jsonNode.get(businessIdKey).asText(),jsonNode);
            }
            return jsonNodeMap;
        }
        return null;
    }


    private List<BusinessLogNodeInfo> toLogNodeInfo(Map<String, JsonNode> beforeMap, Map<String, JsonNode> afterMap,String businessIdKey,String businessNoKey){
        List<BusinessLogNodeInfo> logNodeInfos = new ArrayList<>();
        //处理修改和删除
        if(beforeMap!=null && !beforeMap.isEmpty()){
            for (String s : beforeMap.keySet()) {
                //修改
                if(afterMap!=null && afterMap.containsKey(s)){
                    JsonNode before = beforeMap.get(s);
                    BusinessLogNodeInfo businessLogNodeInfo = new BusinessLogNodeInfo();
                    businessLogNodeInfo.setBefore(beforeMap.get(s));
                    businessLogNodeInfo.setAfter(afterMap.get(s));
                    businessLogNodeInfo.setMark(200);
                    String businessId = before.get(businessIdKey).asText("");
                    if(StrUtil.isBlank(businessId)){
                        continue;
                    }
                    businessLogNodeInfo.setBusinessId(businessId);
                    businessLogNodeInfo.setBusinessNo(before.has(businessNoKey)?before.get(businessNoKey).asText(""):"");
                    logNodeInfos.add(businessLogNodeInfo);
                }else{
                    //删除
                    JsonNode before = beforeMap.get(s);
                    BusinessLogNodeInfo businessLogNodeInfo = new BusinessLogNodeInfo();
                    businessLogNodeInfo.setBefore(beforeMap.get(s));
                    businessLogNodeInfo.setMark(300);
                    String businessId = beforeMap.get(s).get(businessIdKey).asText("");
                    if(StrUtil.isBlank(businessId)){
                        continue;
                    }
                    businessLogNodeInfo.setBusinessId(businessId);
                    businessLogNodeInfo.setBusinessNo(before.has(businessNoKey)?before.get(businessNoKey).asText(""):"");
                }
            }
        }

        //处理新增
        if(afterMap!=null && !afterMap.isEmpty()){
            for (String s : afterMap.keySet()) {
                if(beforeMap!=null && !beforeMap.containsKey(s)){
                    JsonNode after = afterMap.get(s);
                    BusinessLogNodeInfo businessLogNodeInfo = new BusinessLogNodeInfo();
                    businessLogNodeInfo.setAfter(afterMap.get(s));
                    businessLogNodeInfo.setMark(100);
                    String businessId = after.has(businessIdKey)?after.get(businessIdKey).asText(""):"";
                    if(StrUtil.isBlank(businessId)){
                        continue;
                    }
                    businessLogNodeInfo.setBusinessId(businessId);
                    businessLogNodeInfo.setBusinessNo(after.has(businessNoKey)?after.get(businessNoKey).asText(""):"");
                    logNodeInfos.add(businessLogNodeInfo);
                }
            }
        }

        return logNodeInfos;
    }

    /**
    * @description：将list对象合并为新的对象，方便后续逻辑处理
    * @author：陈超超
    * @time：2024/11/4 10:04
    */
    private List<BusinessLogNodeInfo> toLogNodeInfo(List<R> before, List<R> after) throws IOException {
        List<BusinessLogItemConfig> itemConfigs = config();
        if(CollUtil.isEmpty(itemConfigs)){
            log.info("未配置业务日志字段，无法输出日志");
            return new ArrayList<>();
        }
        String businessIdKey = itemConfigs.stream().filter(itemConfig -> itemConfig.getItemPrimaryKeyTypes()!=null && itemConfig.getItemPrimaryKeyTypes().contains(ItemPrimaryKeyType.BUSINESS_ID)).findFirst().orElse(new BusinessLogItemConfig()).getEnName();
        String businessNoKey = itemConfigs.stream().filter(itemConfig -> itemConfig.getItemPrimaryKeyTypes()!=null && itemConfig.getItemPrimaryKeyTypes().contains(ItemPrimaryKeyType.BUSINESS_NO)).findFirst().orElse(new BusinessLogItemConfig()).getEnName();
        if(StrUtil.isBlank(businessIdKey)){
            log.info("未配置业务主键，无法输出日志");
            return new ArrayList<>();
        }
        JsonNode beforeNode = objectMapper.readTree(objectMapper.writeValueAsBytes(before));
        JsonNode afterNode = objectMapper.readTree(objectMapper.writeValueAsBytes(after));
        Map<String, JsonNode> beforeMap = jsonNodeMap(beforeNode, businessIdKey);
        Map<String,JsonNode> afterMap = jsonNodeMap(afterNode, businessIdKey);
        return toLogNodeInfo(beforeMap,afterMap,businessIdKey,businessNoKey);
    }

    /**
    * @description：aop代理
    * @author：陈超超
    * @time：2024/11/1 14:07
    */
    @Override
    public void outPut(List<R> before, List<R> after, BusinessLog businessLog) throws IOException {
        if(CollUtil.isNotEmpty(before) || CollUtil.isNotEmpty(after)){
            List<BusinessLogNodeInfo> list = toLogNodeInfo(before,after);
            List<BusinessLogPo> businessLogPoList = new ArrayList<>();
            List<BusinessDetailLogPO> businessDetailLogPOList = new ArrayList<>();

            for (BusinessLogNodeInfo businessLogNodeInfo : list) {
                BusinessLogPo businessLogPo = new BusinessLogPo();
                businessLogPo.setBusinessId(businessLogNodeInfo.getBusinessId());
                businessLogPo.setBusinessNo(businessLogNodeInfo.getBusinessNo());
                businessLogPo.setOperationType(businessLog.operationType().getCode());
                businessLogPo.setOperationName(businessLog.path().substring(businessLog.path().lastIndexOf(":")));
                businessLogPo.setPath(businessLog.path());
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                businessLogPo.setIp(requestAttributes != null ? ServletUtil.getClientIP(requestAttributes.getRequest()) :"");
                businessLogPo.setBatchNo(IdUtil.simpleUUID());
                businessLogPoList.add(businessLogPo);
                businessDetailLogPOList.addAll(parse(ListUtil.toList(businessLogNodeInfo),config(),0L));
            }

            if(!businessLogPoList.isEmpty()){
                businessLogMapper.insert(businessLogPoList);
            }
            if(!businessDetailLogPOList.isEmpty()){
                businessDetailLogMapper.insert(businessDetailLogPOList);
            }
        }
    }


    /**
     * 代码手动添加业务操作日志
     * @param manualInsertDto
     * @param before
     * @param after
     */
    public void outPut(BusinessLogManualInsertDto manualInsertDto, List<R> before, List<R> after ) throws IOException {
        if(CollUtil.isNotEmpty(before) || CollUtil.isNotEmpty(after)){
            List<BusinessLogNodeInfo> list = toLogNodeInfo(before,after);
            List<BusinessLogPo> businessLogPoList = new ArrayList<>();
            List<BusinessDetailLogPO> businessDetailLogPOList = new ArrayList<>();

            for (BusinessLogNodeInfo businessLogNodeInfo : list) {
                BusinessLogPo businessLogPo = new BusinessLogPo();
                businessLogPo.setBusinessId(businessLogNodeInfo.getBusinessId());
                businessLogPo.setBusinessNo(businessLogNodeInfo.getBusinessNo());
                businessLogPo.setOperationType(manualInsertDto.getOperationType().getCode());
                businessLogPo.setOperationName(manualInsertDto.getPath().substring(manualInsertDto.getPath().lastIndexOf(":")));
                businessLogPo.setPath(manualInsertDto.getPath());
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                businessLogPo.setIp(requestAttributes != null ? ServletUtil.getClientIP(requestAttributes.getRequest()) :"");
                businessLogPo.setBatchNo(IdUtil.simpleUUID());
                businessLogPo.setRemark(manualInsertDto.getRemark());
                businessLogPoList.add(businessLogPo);
                businessDetailLogPOList.addAll(parse(ListUtil.toList(businessLogNodeInfo),config(),0L));
            }

            if(!businessLogPoList.isEmpty()){
                businessLogMapper.insert(businessLogPoList);
            }
            if(!businessDetailLogPOList.isEmpty()){
                businessDetailLogMapper.insert(businessDetailLogPOList);
            }
        }
    }



    private List<BusinessDetailLogParseInfo> parse(List<BusinessLogNodeInfo> list,List<BusinessLogItemConfig> configs,Long parentId){
        LinkedList<BusinessDetailLogParseInfo> result = new LinkedList<>();
        for (BusinessLogItemConfig config : configs) {
            for (BusinessLogNodeInfo businessLogNodeInfo : list) {
                JsonNode beforeNode = businessLogNodeInfo.getBefore()==null?objectMapper.createObjectNode():businessLogNodeInfo.getBefore();
                JsonNode afterNode = businessLogNodeInfo.getAfter()==null?objectMapper.createObjectNode():businessLogNodeInfo.getAfter();
                BusinessLogItemFormatter formatter = config.getFormatter();
                String beforeValue = beforeNode.has(config.getEnName())?beforeNode.get(config.getEnName()).asText(""):"";
                beforeValue = formatter!=null?formatter.format(beforeValue):beforeValue;
                String afterValue = afterNode.has(config.getEnName())?afterNode.get(config.getEnName()).asText(""):"";
                afterValue = formatter!=null?formatter.format(afterValue):afterValue;
                BusinessDetailLogParseInfo parseInfo = new BusinessDetailLogParseInfo();
                parseInfo.setId(IdWorker.getId());
                parseInfo.setBeforeValue(beforeValue);
                parseInfo.setAfterValue(afterValue);
                parseInfo.setName(config.getName());
                parseInfo.setEnName(config.getEnName());
                parseInfo.setContentType(config.getContentType().getCode());
                parseInfo.setOperationType(businessLogNodeInfo.getMark());
                parseInfo.setParentId(parentId);
                if(config.getConfigs()!=null){
                    String businessIdKey = config.getConfigs().stream().filter(itemConfig -> itemConfig.getItemPrimaryKeyTypes()!=null && itemConfig.getItemPrimaryKeyTypes().contains(ItemPrimaryKeyType.BUSINESS_ID)).findFirst().orElse(new BusinessLogItemConfig()).getEnName();
                    String businessNoKey = config.getConfigs().stream().filter(itemConfig -> itemConfig.getItemPrimaryKeyTypes()!=null && itemConfig.getItemPrimaryKeyTypes().contains(ItemPrimaryKeyType.BUSINESS_NO)).findFirst().orElse(new BusinessLogItemConfig()).getEnName();
                    Map<String,JsonNode> beforeMap = jsonNodeMap(beforeNode.get(config.getEnName()), businessIdKey);
                    Map<String,JsonNode> afterMap = jsonNodeMap(afterNode.get(config.getEnName()), businessIdKey);
                    if(StrUtil.isBlank(businessIdKey)){
                        log.info("未配置业务主键，无法输出日志");
                        return new ArrayList<>();
                    }
                    List<BusinessLogNodeInfo> nodeInfos = toLogNodeInfo(beforeMap,afterMap,businessIdKey,businessNoKey);
                    List<BusinessDetailLogParseInfo> child = parse(nodeInfos,config.getConfigs(),parseInfo.getId());
                    if(child.stream().anyMatch(j->!j.getAfterValue().equals(j.getBeforeValue()))){
                        result.add(parseInfo);
                        result.addAll(child);
                    }
                }else if(!parseInfo.getAfterValue().equals(parseInfo.getBeforeValue())){
                    result.add(parseInfo);
                }
            }
        }
        return result;
    }
}
