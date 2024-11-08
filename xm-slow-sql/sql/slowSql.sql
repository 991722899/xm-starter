set names utf8mb4;
use test;
drop table if exists sys_slow_sql;


drop table if exists sys_slow_sql;
create table sys_slow_sql
(
    id          bigint       not null
        primary key,
    sql_text    text         not null comment 'SQL语句',
    stack_info text null comment  '程序堆栈',
    mapper_id  varchar(200) null comment 'mapperId',
    time_consuming bigint not null comment '耗时毫秒',
    sort        int          not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '慢sql' charset = utf8mb4;
create index mapper_id on sys_slow_sql (mapper_id);