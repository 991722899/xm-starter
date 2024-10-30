drop table if exists sys_user;
create table sys_user
(
    id               bigint(30)         not null comment '主键' primary key,
    salt_value        varchar(50)        null comment '盐值，用户创建时生成，作为密码加密的盐值',
    user_name        varchar(50)        not null comment '用户名',
    user_real_name   varchar(50)        null comment '真实姓名',
    user_password     varchar(50)        not null comment '密码',
    user_email       varchar(50)        null comment '邮箱',
    user_phone       varchar(50)        null comment '手机号',
    user_status      int        not null default 0 comment '状态 0正常 1禁用',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '系统用户' charset = utf8mb4;
create unique index user_name_unique on sys_user (user_name);
create index user_name
    on sys_user (user_name);
create index user_real_name
    on sys_user (user_real_name);
create index user_email
    on sys_user (user_email);
create index user_phone
    on sys_user (user_phone);
create index user_status
    on sys_user (user_status);

drop table if exists sys_menu;
create table sys_menu
(
    id               bigint(30)         not null comment '主键' primary key,
    menu_name        varchar(50)        not null comment '菜单名',
    menu_code        varchar(50)         null comment '菜单编码',
    menu_router       varchar(50)        null comment '路由',
    menu_icon        varchar(50)        null comment '图标',
    menu_parent_id   bigint(30)         null comment '父级菜单',
    menu_status      int        not null default 0 comment '状态 0正常 1禁用',
    menu_type        int       not null default 100 comment '类型 100菜单 200按钮 300接口',
    sort             int       not null default 0 comment '排序',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '系统菜单按钮' charset = utf8mb4;

create index menu_name
    on sys_menu (menu_name);
create index menu_code
    on sys_menu (menu_code);
create index menu_router
    on sys_menu (menu_router);
create index menu_parent_id
    on sys_menu (menu_parent_id);
create index menu_status
    on sys_menu (menu_status);
create index menu_type
    on sys_menu (menu_type);

drop table if exists sys_role;
create table sys_role
(
    id               bigint(30)         not null comment '主键' primary key,
    role_name        varchar(50)        not null comment '角色名',
    role_code        varchar(50)         null comment '角色编码',
    role_status      int        not null default 0 comment '状态 0正常 1禁用',
    sort             int       not null default 0 comment '排序',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '系统角色' charset = utf8mb4;

create index role_name
    on sys_role (role_name);
create index role_code
    on sys_role (role_code);
create index role_status
    on sys_role (role_status);

drop table if exists sys_user_role;
create table sys_user_role
(
    id               bigint(30)         not null comment '主键' primary key,
    user_id          bigint(30)         not null comment '用户id',
    role_id          bigint(30)         not null comment '角色id',
    sort             int       not null default 0 comment '排序',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '系统用户角色' charset = utf8mb4;

create index user_id
    on sys_user_role (user_id);
create index role_id
    on sys_user_role (role_id);



drop table if exists sys_role_menu;
create table sys_role_menu
(
    id               bigint(30)         not null comment '主键' primary key,
    role_id          bigint(30)         not null comment '角色id',
    menu_id          bigint(30)         not null comment '菜单id',
    sort             int       not null default 0 comment '排序',
    create_time      datetime           null comment '创建时间',
    create_id   varchar(50)        null comment '创建人ID',
    update_time      datetime           null comment '修改时间',
    update_id   varchar(50)        null comment '修改人ID',
    create_name varchar(50)        null comment '创建人',
    update_name varchar(50)        null comment '修改人',
    remarks          varchar(500)       null comment '备注'
)
    comment '系统角色菜单' charset = utf8mb4;

create index role_id
    on sys_role_menu (role_id);
create index menu_id
    on sys_role_menu (menu_id);





