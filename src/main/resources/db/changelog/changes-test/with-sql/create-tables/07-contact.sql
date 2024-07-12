create table contact
(
    id              int auto_increment
        primary key,
    patient_id      int null,
    doctor_id       int null,
    status          int null,
    deleted_by_user int null,
    constraint FK1pii9c87wntl182twslyyt2h8
        foreign key (doctor_id) references doctor (id),
    constraint FK2ygsb9t4baokpt9iqb5b9pdcc
        foreign key (patient_id) references patient (id)
);

