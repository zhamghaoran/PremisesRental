create table `Premises rental`.place
(
    id          int auto_increment comment '场地id
'
        primary key,
    type        varchar(100)                        null comment '场地类型',
    cost        int                                 null comment '场地单价
',
    create_time timestamp default CURRENT_TIMESTAMP null,
    update_time timestamp default CURRENT_TIMESTAMP null,
    available   bigint                              null comment '用一个数字来表示一个场地是否已经被出租。
',
    constraint id
        unique (id)
)
    comment '场地信息';

