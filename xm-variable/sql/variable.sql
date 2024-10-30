set names utf8mb4;
use test;
drop table if exists sys_variable;


drop table if exists sys_variable;
create table sys_variable
(
    id          bigint       not null
        primary key,
    title  varchar(50)  not null comment '变量名称',
    variable_key varchar(50)  not null comment '变量KEY',
    variable_value text not null comment '变量值',
    remarks     varchar(500) null comment '备注',
    sort        int          not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)     comment '系统变量' charset = utf8mb4;
create unique index variable_key
    on sys_variable (variable_key);