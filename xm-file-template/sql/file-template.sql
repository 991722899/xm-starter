set names utf8mb4;
use test;

drop table if exists sys_file_template;
create table sys_file_template
(
    id          bigint       not null
        primary key,
    title  varchar(50)  not null comment '模板名称',
    template_key varchar(50)  not null comment '模板KEY',
    file_batch_no varchar(50)  not null comment '模板文件批次号',
    remarks     varchar(500) null comment '备注',
    sort        int          not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '文件模块' charset = utf8mb4;
create unique index template_key
    on sys_file_template (template_key);

