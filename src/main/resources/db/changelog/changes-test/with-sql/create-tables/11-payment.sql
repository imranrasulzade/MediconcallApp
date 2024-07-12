create table payment
(
    id            int auto_increment
        primary key,
    sender_id     int          null,
    sender_card   varchar(255) null,
    receiver_id   int          null,
    receiver_card varchar(255) null,
    amount        int          null,
    timestamp     datetime(6)  null,
    status        int          null,
    constraint FKd8cr6jjfhvdilg46qoha5a1i2
        foreign key (sender_id) references patient (id),
    constraint FKqxpr1ceum6yiv4erb3bermswm
        foreign key (receiver_id) references doctor (id)
);

