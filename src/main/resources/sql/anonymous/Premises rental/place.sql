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
    shop_id     int                                 not null comment 'blong to which placeId',
    constraint id
        unique (id),
    constraint place_place_id_fk
        foreign key (shop_id) references `Premises rental`.place (id)
)
    comment '场地信息';

