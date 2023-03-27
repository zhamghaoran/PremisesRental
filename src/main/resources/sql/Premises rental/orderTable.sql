create table `Premises rental`.orderTable
(
    id          bigint auto_increment comment '订单id'
        primary key,
    user_id     bigint                              not null comment '用户id
',
    shop_id     bigint                              not null comment '商铺id',
    place_id    bigint                              not null comment '场地id',
    begin_time  timestamp                           null comment '开始时间',
    rent_time   int                                 not null comment '租赁时间',
    create_time timestamp default CURRENT_TIMESTAMP null,
    constraint id
        unique (id)
);

