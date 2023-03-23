create table `Premises rental`.`order`
(
    id          int auto_increment comment '订单id'
        primary key,
    user_id     int                                 not null comment '用户id
',
    shop_id     int                                 not null comment '商铺id',
    place_id    int                                 not null comment '场地id',
    begin_time  int                                 null comment '开始时间',
    rent_time   int                                 not null comment '租赁时间',
    create_time timestamp default CURRENT_TIMESTAMP null,
    constraint id
        unique (id)
);

