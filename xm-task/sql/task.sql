set names utf8mb4;
use test;
drop table if exists sys_task_config;
create table sys_task_config
(
    id          bigint       not null
        primary key,
    code        varchar(50)  not null comment '任务编码',
    name  varchar(50)  not null comment '任务名称',
    bean_name varchar(50)  not null comment 'bean名称',
    run_type    int not null default 100 comment '运行类型 100同步 200异步',
    group_code varchar(50)  not null comment '组编码',
    remarks     varchar(500) null comment '备注',
    sort        int          not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '任务配置表' charset = utf8mb4;
create unique index code
    on sys_task_config (code);
create  index name
    on sys_task_config (name);
create  index bean_name
    on sys_task_config (bean_name);
create  index run_type
    on sys_task_config (run_type);
create  index group_code
    on sys_task_config (group_code);

insert into sys_task_config(id,code,name,bean_name,run_type,group_code,remarks,sort,create_id,create_name,create_time) values
 (1, 'task_test', '测试任务', 'taskExecuteTest', 100, 'default', '测试任务', 1, '1', 'admin', '2020-01-01 00:00:00');


drop table if exists sys_task;
create table sys_task
(
    id          bigint       not null primary key,
    config_id bigint not null comment '配置ID',
    name varchar(50)   null comment '任务名称或文件名称',
    status      int not null default 100 comment '状态 100待执行 200执行中 300执行成功 400执行失败',
    begin_time datetime     not null comment '开始时间',
    end_time datetime     null comment '结束时间',
    params text null comment '请求参数',
    download_url varchar(1000) null comment '下载地址',
    download_batch_no varchar(50) null comment '下载批次号',
    exception_msg longtext null comment '异常信息',
    msg_id varchar(50) null comment '消息ID',
    remarks     varchar(500) null comment '备注',
    sort        int default 0         not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '任务表' charset = utf8mb4;

create  index config_id
    on sys_task (config_id);
create  index name
    on sys_task (name);
create  index status
    on sys_task (status);
create  index begin_time
    on sys_task (begin_time);
create  index end_time
    on sys_task (end_time);
create  index download_batch_no
    on sys_task (download_batch_no);
create  index msg_id
    on sys_task (msg_id);