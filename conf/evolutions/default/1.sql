# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table address (
  primary_key               bigint not null,
  address_id                varchar(255),
  street                    varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  zip_code                  varchar(255),
  warehouse_primary_key     bigint,
  constraint pk_address primary key (primary_key))
;

create table product (
  primary_key               bigint not null,
  product_id                varchar(255) not null,
  name                      varchar(255),
  description               varchar(255),
  constraint uq_product_product_id unique (product_id),
  constraint pk_product primary key (primary_key))
;

create table stock_item (
  primary_key               bigint not null,
  stock_item_id             varchar(255) not null,
  warehouse_primary_key     bigint,
  product_primary_key       bigint,
  quantity                  bigint,
  constraint uq_stock_item_stock_item_id unique (stock_item_id),
  constraint pk_stock_item primary key (primary_key))
;

create table tag (
  primary_key               bigint not null,
  tag_id                    varchar(255) not null,
  constraint uq_tag_tag_id unique (tag_id),
  constraint pk_tag primary key (primary_key))
;

create table warehouse (
  primary_key               bigint not null,
  warehouse_id              varchar(255) not null,
  name                      varchar(255),
  constraint uq_warehouse_warehouse_id unique (warehouse_id),
  constraint pk_warehouse primary key (primary_key))
;


create table product_tag (
  product_primary_key            bigint not null,
  tag_primary_key                bigint not null,
  constraint pk_product_tag primary key (product_primary_key, tag_primary_key))
;
create sequence address_seq;

create sequence product_seq;

create sequence stock_item_seq;

create sequence tag_seq;

create sequence warehouse_seq;

alter table address add constraint fk_address_warehouse_1 foreign key (warehouse_primary_key) references warehouse (primary_key) on delete restrict on update restrict;
create index ix_address_warehouse_1 on address (warehouse_primary_key);
alter table stock_item add constraint fk_stock_item_warehouse_2 foreign key (warehouse_primary_key) references warehouse (primary_key) on delete restrict on update restrict;
create index ix_stock_item_warehouse_2 on stock_item (warehouse_primary_key);
alter table stock_item add constraint fk_stock_item_product_3 foreign key (product_primary_key) references product (primary_key) on delete restrict on update restrict;
create index ix_stock_item_product_3 on stock_item (product_primary_key);



alter table product_tag add constraint fk_product_tag_product_01 foreign key (product_primary_key) references product (primary_key) on delete restrict on update restrict;

alter table product_tag add constraint fk_product_tag_tag_02 foreign key (tag_primary_key) references tag (primary_key) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists address;

drop table if exists product;

drop table if exists product_tag;

drop table if exists stock_item;

drop table if exists tag;

drop table if exists warehouse;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists address_seq;

drop sequence if exists product_seq;

drop sequence if exists stock_item_seq;

drop sequence if exists tag_seq;

drop sequence if exists warehouse_seq;

