create table products
(
    id          bigint         not null
        primary key,
    brand       varchar(255)   not null,
    category    varchar(255)   not null,
    created_at  timestamp(6)   not null,
    description text           not null,
    image       varchar(255)   not null,
    name        varchar(255)   not null,
    price       numeric(38, 2) not null,
    stock       integer        not null,
    updated_at  timestamp(6)   not null
);

alter table products
    owner to ecommerce;