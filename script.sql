create table users
(
    id       bigint auto_increment
        primary key,
    username varchar(255) not null UNIQUE,
    email    varchar(255) not null UNIQUE,
    password varchar(255) not null
);

create table addresses
(
    id      bigint auto_increment
        primary key,
    street  varchar(255) not null,
    city    varchar(255) not null,
    zipcode varchar(255) not null,
    user_id bigint       not null,
    constraint addresses_users_id_fk
        foreign key (user_id) references users (id)
);


