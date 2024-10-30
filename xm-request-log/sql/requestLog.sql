drop table if exists sys_request_log;
create table sys_request_log
(
    id               bigint(30)         not null comment '主键' primary key,
    title             varchar(200)       not null comment '标题',
    status            varchar(20)       not null default 'normal' comment '状态，正常normal，异常exception',
    time_consuming int not null default 0 comment '耗时，毫秒',
    exception_detail text comment '异常详情',
    ip       varchar(50) not null comment 'ip',
    request_url         varchar(500)        null comment '请求地址',
    request_method      varchar(20)       null comment '请求方式 get,post',
    user_id    varchar(50)       null comment '用户ID',
    user_name    varchar(50)       null comment '用户名',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '接口请求日志' charset = utf8mb4;

create index user_id
    on sys_request_log (user_id);
create index title
    on sys_request_log (title);
create index user_name
    on sys_request_log (user_name);
create index status
    on sys_request_log (status);


drop table if exists sys_request_log_detail;
create table sys_request_log_detail
(
    id               bigint(30)         not null comment '主键' primary key,
    request_log_id bigint(30) not null comment '日志ID',
    type varchar(10) not null comment 'request请求，response响应',
    content_type varchar(10) not null comment 'header,content',
    content_key varchar(100) comment 'key',
    content_value text  comment 'value',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '接口请求日志明细' charset = utf8mb4;

create index request_log_id
    on sys_request_log_detail (request_log_id);
create index type
    on sys_request_log_detail (type);
create index content_type
    on sys_request_log_detail (content_type);

