create table patient
(
    id            int auto_increment
        primary key,
    user_id       int          null,
    document_path varchar(255) null,
    bank_account  varchar(255) null,
    status        int          not null,
    constraint UK_6i3fp8wcdxk473941mbcvdao4
        unique (user_id),
    constraint FKp6ttmfrxo2ejiunew4ov805uc
        foreign key (user_id) references user (id)
);

