-- auto-generated definition
drop table if exists sys_file;
create table sys_file
(
    id               bigint(30)         not null comment '主键'
        primary key,
    storage_type       varchar(20) default 'local' not null default 'local' comment 'local本地,oss阿里云',
    batch_no         varchar(50)        null comment '唯一批次号',
    business_type    varchar(255)       null comment '业务类型定义唯一KEY，表示此数据为某个模块某个字段的文件',
    business_id      varchar(50)        not null comment '业务ID',
    business_no       varchar(50)        not null comment '业务编号',
    content_type             varchar(255)       null comment '文件类型',
    original_name             varchar(255)       null comment '文件原始名称',
    new_name         varchar(255)       null comment '新名称,文件存储名称',
    path             varchar(1000)      null comment '存储地址',
    access_url       varchar(2000)      null comment '文件访问地址',
    url_expiration   datetime           null comment '文件访问地址过期时间',
    length           int                null comment '文件长度(字节)',
    width            int                null comment '图片宽度',
    height           int                null comment '图片高度',
    main_file        smallint default 0 null comment '是否主图0否，1是',
    temporary_file   smallint default 0 null comment '默认0为临时文件1非临时文件',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '文件' charset = utf8mb4;

create index batch_no
    on sys_file (batch_no);
create index storage_type
    on sys_file (storage_type);

