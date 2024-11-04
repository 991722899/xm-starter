package com.xm.starter.business.log.model;

import cn.hutool.core.collection.ListUtil;
import com.xm.starter.business.log.formatter.BaseFormatter;
import com.xm.starter.business.log.formatter.BusinessLogItemFormatter;
import lombok.Data;

import java.util.List;

/**
* @description：日志项配置
* @author：陈超超
* @time：2024/11/1 11:41
*/
@Data
public class BusinessLogItemConfig {
    /**
     * 中文名称
     */
    private String name;
    /**
     * 字段明细
     */
    private String enName;
    /**
     * 脚本
     */
    private String scriptStr;
    /**
     * 子项
     */
    private List<BusinessLogItemConfig> configs;

    /**
     * 内容类型
     */
    private ItemContentType contentType = ItemContentType.TEXT;

    /**
     * 格式化
     */
    private BusinessLogItemFormatter formatter = new BaseFormatter();

    /**
     * 逻辑类型
     */
    private List<BusinessLogLogicType> logicTypes = ListUtil.toList(BusinessLogLogicType.COMPARE);

    /**
     * 此项对应的关键字段，用来保存日志时从对象中取businessId和businessNo
     */
    private List<ItemPrimaryKeyType> itemPrimaryKeyTypes;

    public BusinessLogItemConfig() {
    }

    public BusinessLogItemConfig(String name, String enName) {
        this.name = name;
        this.enName = enName;
    }

    public BusinessLogItemConfig(String name, String enName, BusinessLogItemFormatter formatter) {
        this.name = name;
        this.enName = enName;
        this.formatter = formatter;
    }

    public BusinessLogItemConfig(String name, String enName, List<BusinessLogItemConfig> configs) {
        this.name = name;
        this.enName = enName;
        this.configs = configs;
    }

    public BusinessLogItemConfig(String name, String enName, List<BusinessLogItemConfig> configs, List<ItemPrimaryKeyType> itemPrimaryKeyTypes) {
        this.name = name;
        this.enName = enName;
        this.configs = configs;
        this.itemPrimaryKeyTypes = itemPrimaryKeyTypes;
    }

    public BusinessLogItemConfig(String name, String enName, List<BusinessLogItemConfig> configs, BusinessLogItemFormatter formatter) {
        this.name = name;
        this.enName = enName;
        this.configs = configs;
        this.formatter = formatter;
    }

    public BusinessLogItemConfig(String name, String enName, String scriptStr) {
        this.name = name;
        this.enName = enName;
        this.scriptStr = scriptStr;
    }

    public BusinessLogItemConfig(String name, String enName, String scriptStr, List<BusinessLogItemConfig> configs) {
        this.name = name;
        this.enName = enName;
        this.scriptStr = scriptStr;
        this.configs = configs;
    }
}
