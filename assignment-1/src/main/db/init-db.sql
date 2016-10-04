-- connect as 'root'
create database easybuy default character set utf8 collate utf8_general_ci;
create user 'easybuy'@'%' identified by 'easybuy';
grant all privileges on easybuy.* to 'easybuy'@'%';
flush privileges;


drop table if exists easybuy.easybuy_user;
drop table if exists easybuy.easybuy_news;
drop table if exists easybuy.easybuy_comment;
drop table if exists easybuy.easybuy_product;
drop table if exists easybuy.easybuy_product_category;
drop table if exists easybuy.easybuy_order;
drop table if exists easybuy.easybuy_order_detail;

create table easybuy.easybuy_user (
    eu_user_id int not null auto_increment,
    eu_user_name varchar(200),
    eu_password varchar(200),
    eu_sex varchar(10),
    eu_birthday date,
    eu_identity_code varchar(200),
    eu_email varchar(400),
    eu_mobile varchar(100),
    eu_address varchar(400),
    eu_status decimal(1) default 1,
    primary key (eu_user_id)
);

create table easybuy.easybuy_news (
    en_id int not null auto_increment,
    en_title varchar(400),
    en_content text,
    en_create_time timestamp default current_timestamp,
    primary key (en_id)
);

create table easybuy.easybuy_comment (
    ec_id int not null auto_increment,
    ec_reply varchar(400),
    ec_content text,
    ec_create_time timestamp default current_timestamp,
    ec_reply_time timestamp,
    ec_nick_name varchar(200),
    primary key (ec_id)
);

create table easybuy.easybuy_product (
    ep_id int not null auto_increment,
    ep_name varchar(400),
    ep_description text,
    ep_price decimal(12,3),
    ep_stock int,
    epc_id int,
    epc_child_id int,
    ep_file_name varchar(400),
    primary key (ep_id)
);

create table easybuy.easybuy_product_category (
    epc_id int not null auto_increment,
    epc_name varchar(400),
    epc_parent_id int,
    primary key (epc_id)
);

create table easybuy.easybuy_order (
    eo_id int not null auto_increment,
    eo_user_id int,
    eo_user_name varchar(200),
    eo_user_address varchar(400),
    eo_create_time timestamp default current_timestamp,
    eo_cost decimal(12,3),
    eo_status decimal(1),
    eo_type decimal(1),
    primary key (eo_id)
);

create table easybuy.easybuy_order_detail (
    eod_id int not null auto_increment,
    eo_id int,
    ep_id int,
    eod_quantity int,
    eod_cost decimal(12,3),
    primary key (eod_id)
);

insert into easybuy.easybuy_user (eu_user_name, eu_password, eu_email, eu_mobile, eu_status) values ('admin','admin','foo@bar.baz','18900000000',2);
insert into easybuy.easybuy_user (eu_user_name, eu_password, eu_email, eu_mobile, eu_status) values ('user1','user1','user1@bar.baz','18900000011',1);
insert into easybuy.easybuy_user (eu_user_name, eu_password, eu_email, eu_mobile, eu_status) values ('user2','user2','user2@bar.baz','18900000022',1);

commit;

