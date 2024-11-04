drop table if exists sys_business_log;
create table sys_business_log
(
    id          bigint(30)       not null
        primary key,
    business_id varchar(50)  not null comment '业务id',
    business_no varchar(50)  not null comment '业务编号',
    remark varchar(255) null comment '备注',
    ip varchar(50)  not null comment 'ip',
    batch_no varchar(50)  not null comment '批次号',
    operation_type int default 0 not null comment '操作类型 100添加 200修改 300删除 400导出',
    operation_name varchar(50)  not null comment '操作名称',
    path  varchar(255)  not null comment '菜单按钮路径',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)    comment '业务操作日志主表' charset = utf8mb4;
create index business_id on sys_business_log (business_id);
create index business_no on sys_business_log (business_no);
create index ip on sys_business_log (ip);
create index ip on sys_business_log (ip);
create index batch_no on sys_business_log (batch_no);
create index operation_type on sys_business_log (operation_type);

drop table if exists sys_business_detail_log;
create table sys_business_detail_log
(
    id          bigint       not null
        primary key,
    log_id bigint(30) not null comment '日志id',
    operation_type int default 0 not null comment '操作类型 100添加 200修改 300删除 400导出',
    name varchar(50)  not null comment '字段名称',
    en_name varchar(50)  not null comment '字段英文名',
    content_type int default 0 not null comment '内容类型 100文本 200文本域 300数字 400布尔值 500日期 600图片 700文件 800日期时间',
    before_value varchar(50)  null comment '修改前内容',
    after_value varchar(50)  null comment '修改后内容',
    remark varchar(255) null comment '备注',
    parent_id bigint(30) not null comment '父级id',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)    comment '业务操作日志子表' charset = utf8mb4;
create index log_id on sys_business_detail_log (log_id);
create index operation_type on sys_business_detail_log (operation_type);
create index en_name on sys_business_detail_log (en_name);
create index content_type on sys_business_detail_log (content_type);
create index name on sys_business_detail_log (name);
