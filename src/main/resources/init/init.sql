drop table if exists order_info;
create table order_info(
    id bigint unsigned primary key comment '订单id',
    name varchar(50) not null comment '订单名称',
    message_id varchar(36) not null comment '消息id'
)engine =innodb, charset=utf8;

drop table if exists order_message;
create table order_message(
    id VARCHAR(36) primary key comment '消息id',
    message varchar(4000) not null comment '消息内容',
    status tinyint not null default 0 comment '发送状态，0 发送中，1 发送成功， 2 发送失败',
    retry_times tinyint not null default 0 comment '重试次数',
    next_retry_time timestamp comment '下次发送时间',
    create_time timestamp comment '创建时间',
    update_time timestamp comment '最后修改时间'
)engine =innodb, charset = utf8;