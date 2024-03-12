CREATE DATABASE IF NOT EXISTS `cloud_desktop`;
USE `cloud_desktop`;

create table user
(
    id          bigint auto_increment comment '主键',
    username    varchar(32) collate utf8_bin              not null comment '用户名',
    email       varchar(64) collate utf8_bin              not null comment '邮箱',
    phone       varchar(11) collate utf8_bin default null comment '手机号',
    password    varchar(64) collate utf8_bin              not null comment '密码',
    create_time datetime                                  not null comment '创建时间',
    update_time datetime                     default null comment '最后更新时间',
    update_user datetime                     default null comment '更新人ID',
    is_active   boolean                      default true not null comment '账号是否启用',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`username`)
)
    comment '用户表' collate = utf8_bin;

create table role
(
    id         bigint auto_increment comment '主键',
    name       varchar(32) collate utf8_bin not null comment '角色名',
    permission tinyint                      not null comment '权限 1 管理员 2 用户权限',
    primary key (`id`),
    unique key (`name`)
)
    comment '权限表' collate = utf8_bin;

create table user_role
(
    id         bigint auto_increment comment '主键',
    user_id    bigint                       not null comment '用户ID',
    role_id    bigint                       not null comment '角色ID',
    name       varchar(32) collate utf8_bin not null comment '角色名(冗余字段)',
    permission tinyint                      not null comment '权限(冗余字段)',
    primary key (`id`)
)
    comment '用户角色关联表' collate = utf8_bin;

create table image
(
    id          bigint auto_increment comment '主键',
    image_id    varchar(60) collate utf8_bin               not null comment '镜像id',
    name        varchar(50) collate utf8_bin               not null comment '镜像名',
    image       varchar(255) collate utf8_bin default null null comment '镜像图片',
    tag         varchar(10) collate utf8_bin               not null comment '镜像标签',
    size        varchar(10) collate utf8_bin               not null comment '镜像大小',
    create_time datetime                                   not null comment '镜像创建时间',
    delete_time datetime                      default null null comment '镜像删除时间',
    is_active   boolean                       default true not null comment '镜像是否启用',
    user_id     bigint                                     not null comment '镜像所有者ID',
    primary key (`id`),
    unique key (`image_id`)
)
    comment '镜像表' collate = utf8_bin;

create table container
(
    id           bigint auto_increment comment '主键',
    container_id varchar(60) collate utf8_bin               not null comment '容器ID',
    host_port    int                                        not null comment '主机端口映射',
    image_id     bigint                                     not null comment '镜像ID',
    name         varchar(32) collate utf8_bin               not null comment '容器名',
    image        varchar(255) collate utf8_bin default null null comment '容器图片',
    create_time  datetime                                   not null comment '容器创建时间',
    delete_time  datetime                      default null null comment '删除时间',
    user_id      bigint                                     not null comment '所有人ID',
    is_active    boolean                       default true not null comment '容器是否启用',
    status       tinyint                       default 1    not null comment '容器状态',
    primary key (`id`),
    unique key (`container_id`)
)
    comment '容器表' collate = utf8_bin;