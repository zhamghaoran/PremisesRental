create table `Premises rental`.shop
(
    id          int auto_increment comment '商家Id
'
        primary key,
    location    varchar(100)                        not null comment '商铺地址Id',
    create_time timestamp default CURRENT_TIMESTAMP null,
    update_time timestamp default CURRENT_TIMESTAMP null,
    constraint id
        unique (id)
)
    comment '商家的描述';

