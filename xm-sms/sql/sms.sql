set names utf8mb4;
use test;
drop table if exists sys_serial;


drop table if exists sys_sms;
create table sys_sms
(
    id          bigint       not null
        primary key,
    vendor_type varchar(50) default 'aliyun' not null comment '短信供应商 aliyun',
    business_type varchar(50) null  comment '业务类型',
    business_id varchar(50) null  comment '业务ID',
    biz_id varchar(50)  null comment '发送回执 ID',
    request_id varchar(50)   null comment '请求 ID',
    phone  varchar(50)  not null comment '手机号',
    status int  not null comment '100待发送 200发送成功 300发送失败',
    content varchar(500) null comment '短信内容',
    send_time datetime null comment '发送时间',
    exception_msg text null comment '异常信息',
    template_code varchar(50)  null comment '模板编码',
    template_param text null comment '模板参数',
    remarks     varchar(500) null comment '备注',
    sort        int  default 0       not null comment '排序',
    create_id   varchar(50)  not null comment '创建人ID',
    create_name varchar(50)  not null comment '创建人',
    create_time datetime     not null comment '创建时间',
    update_id   varchar(50)  null comment '更新人ID',
    update_name varchar(50)  null comment '更新人',
    update_time datetime     null comment '更新时间'
)
    comment '短信记录' charset = utf8mb4;
create  index phone
    on sys_sms (phone);
create  index send_time
    on sys_sms (send_time);
create  index template_code
    on sys_sms (template_code);
create  index status
    on sys_sms (status);
create  index business_type
    on sys_sms (business_type);
create  index business_id
    on sys_sms (business_id);