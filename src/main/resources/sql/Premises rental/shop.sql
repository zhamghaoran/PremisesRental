create table `Premisesrental`.shop
(
    id          bigint auto_increment comment '商家Id
'
        primary key,
    location    varchar(100)                        not null comment '商铺地址Id',
    create_time timestamp default CURRENT_TIMESTAMP null,
    update_time timestamp default CURRENT_TIMESTAMP null,
    owner_id    bigint                              null comment '属于用户id',
    constraint id
        unique (id),
    constraint shop_user_id_fk
        foreign key (owner_id) references `Premises rental`.user (id)
)
    comment '商家的描述';

