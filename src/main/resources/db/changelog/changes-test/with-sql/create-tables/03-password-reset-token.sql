create table password_reset_token
(
    id          bigint auto_increment
        primary key,
    user_id     int          not null,
    token       varchar(255) null,
    expiry_date datetime(6)  null,
    constraint UK_f90ivichjaokvmovxpnlm5nin
        unique (user_id),
    constraint FK5lwtbncug84d4ero33v3cfxvl
        foreign key (user_id) references user (id)
);

