create table user
(
    id        int auto_increment
        primary key,
    username  varchar(255)                           null,
    password  varchar(255)                           null,
    email     varchar(255)                           null,
    phone     varchar(255)                           null,
    name      varchar(255)                           null,
    surname   varchar(255)                           null,
    gender    int                                    null,
    birthday  datetime(6)                            null,
    address   varchar(255)                           null,
    info      varchar(255)                           null,
    photo_url varchar(255)                           null,
    status    enum ('ACTIVE', 'INACTIVE', 'DELETED') not null
);
