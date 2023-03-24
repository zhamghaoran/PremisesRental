create table user
(
    id          bigint auto_increment comment '用户Id
'
        primary key,
    username    varchar(100)                        not null comment '用户名',
    create_time timestamp default CURRENT_TIMESTAMP null,
    update_time timestamp default CURRENT_TIMESTAMP null,
    phone       varchar(20)                         null,
    constraint id
        unique (id)
)
    comment '用户表';

