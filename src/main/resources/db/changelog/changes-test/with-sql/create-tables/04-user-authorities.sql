create table user_authorities
(
    user_id        int          not null,
    authority_name varchar(255) not null,
    primary key (user_id, authority_name),
    constraint FK93l3o3nqoog62d07mtjxy6sem
        foreign key (authority_name) references authorities (name),
    constraint FKmj13d0mnuj4cd8b6htotbf9mm
        foreign key (user_id) references user (id)
);

