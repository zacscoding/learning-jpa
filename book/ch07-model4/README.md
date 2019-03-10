## 추가 된 요구 사항

- 상품의 종류는 음반, 도서, 영화가 있고 이후 더 확장될 수 있음
- 모든 데이터는 등록일과 수정일이 있어야 함  

> DDL result  

```aidl
Hibernate: 
    drop table category if exists
Hibernate: 
    drop table category_item if exists
Hibernate: 
    drop table delivery if exists
Hibernate: 
    drop table item if exists
Hibernate: 
    drop table member if exists
Hibernate: 
    drop table order_item if exists
Hibernate: 
    drop table orders if exists
Hibernate: 
    drop sequence if exists hibernate_sequence
Hibernate: 
    create table category (
        category_id bigint not null,
        name varchar(255),
        parent_id bigint,
        primary key (category_id)
    )
Hibernate: 
    create table category_item (
        category_id bigint not null,
        item_id bigint not null
    )
Hibernate: 
    create table delivery (
        delivery_id bigint not null,
        city varchar(255),
        status varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (delivery_id)
    )
Hibernate: 
    create table item (
        dtype varchar(31) not null,
        item_id bigint not null,
        name varchar(255),
        price integer not null,
        stock_quantity integer not null,
        actor varchar(255),
        director varchar(255),
        artist varchar(255),
        etc varchar(255),
        author varchar(255),
        isbn varchar(255),
        primary key (item_id)
    )
Hibernate: 
    create table member (
        member_id bigint not null,
        created_date timestamp,
        last_modified_date timestamp,
        city varchar(255),
        name varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (member_id)
    )
Hibernate: 
    create table order_item (
        order_item_id bigint not null,
        count integer not null,
        order_price integer not null,
        item_id bigint,
        order_id bigint,
        primary key (order_item_id)
    )
Hibernate: 
    create table orders (
        order_id bigint not null,
        created_date timestamp,
        last_modified_date timestamp,
        order_date timestamp,
        status varchar(255),
        delivery_id bigint,
        member_id bigint,
        primary key (order_id)
    )
Hibernate: 
    alter table category 
        add constraint FK_81thrbnb8c08gua7tvqj7xdqk 
        foreign key (parent_id) 
        references category
Hibernate: 
    alter table category_item 
        add constraint FK_phkndilpkprh9p1w07pw4xejv 
        foreign key (item_id) 
        references item
Hibernate: 
    alter table category_item 
        add constraint FK_fs4rowhf58nmjxb7vwkes8mdq 
        foreign key (category_id) 
        references category
Hibernate: 
    alter table order_item 
        add constraint FK_flndste1vbvdfb200b4ikibis 
        foreign key (item_id) 
        references item
Hibernate: 
    alter table order_item 
        add constraint FK_5gjhq2fmknk50h8859nf0bcmx 
        foreign key (order_id) 
        references orders
Hibernate: 
    alter table orders 
        add constraint FK_9ct0l8xfeaiqruabcqjh1neui 
        foreign key (delivery_id) 
        references delivery
Hibernate: 
    alter table orders 
        add constraint FK_hk1trr9mwboq35fssa59rnodg 
        foreign key (member_id) 
        references member
Hibernate: 
    create sequence hibernate_sequence start with 1 increment by 1
```