package com.xm.starter.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xm.starter.base.api.UserService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FillMetaObjectHandler implements MetaObjectHandler {
    private @Autowired(required = false) UserService userService;


    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createId", String.class, "create_id");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createName", String.class, "create_name");
        this.strictInsertFill(metaObject, "sort", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateId", String.class, "update_id");
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateName", String.class, "update_name");
    }
}
