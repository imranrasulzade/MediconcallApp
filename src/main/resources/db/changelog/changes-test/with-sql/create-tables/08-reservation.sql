create table reservation
(
    id         int auto_increment
        primary key,
    patient_id int                                                     null,
    doctor_id  int                                                     null,
    date       datetime(6)                                             null,
    status     enum ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') null,
    constraint FKmh96fg24x7cdhex42l051fmhp
        foreign key (doctor_id) references doctor (id),
    constraint FKrrjvkskqqxgliwmqgbl3ijc4n
        foreign key (patient_id) references patient (id)
);

