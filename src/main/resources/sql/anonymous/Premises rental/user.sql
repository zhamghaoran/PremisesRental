create table `Premises rental`.user
(
    id          int auto_increment comment '用户Id
'
        primary key,
    username    varchar(100)                        not null comment '用户名',
    password    varchar(100)                        not null comment '用户密码',
    create_time timestamp default CURRENT_TIMESTAMP null,
    update_time timestamp default CURRENT_TIMESTAMP null,
    constraint id
        unique (id)
)
    comment '用户表';

