set names utf8mb4;
use test;
drop table if exists sys_serial;


drop table if exists sys_serial;
create table sys_serial
(
    id          bigint       not null
        primary key,
    title  varchar(50)  not null comment '序列号名称',
    serial_key varchar(50)  not null comment '序列号KEY',
    current_value bigint(30) DEFAULT NULL COMMENT '当前值',
    min_value bigint(30)  not NULL default 0 COMMENT '最小值',
    max_value bigint(30) not null COMMENT '最大值',
    step_value bigint(30) not NULL COMMENT '增长大小',
    template varchar(200)  not NULL COMMENT '模板',
    template_value varchar(255) DEFAULT NULL COMMENT '模板值，用来控制重置当前值，如一个月内自增',
    remarks     varchar(500) null comment '备注',
    sort        int          not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '序列号' charset = utf8mb4;;
create unique index serial_key
    on sys_serial (serial_key);