create table sys_dict
(
    id          bigint       not null
        primary key,
    dict_label  varchar(50)  not null comment '唯一键',
    dict_value  varchar(200) not null comment '字段值',
    sort        int          not null comment '排序',
    parent_id   bigint       not null comment '父级',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)    comment '数据字典' charset = utf8mb4;

