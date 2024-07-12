create table doctor
(
    id             int auto_increment
        primary key,
    user_id        int          null,
    specialty      varchar(255) null,
    academic_title varchar(255) null,
    qualification  varchar(255) null,
    place_of_work  varchar(255) null,
    bank_account   varchar(16)  null,
    status         int          not null,
    constraint UK_3q0j5r6i4e9k3afhypo6uljph
        unique (user_id),
    constraint FK9roto9ydtnjfkixvexq5vxyl5
        foreign key (user_id) references user (id)
);

