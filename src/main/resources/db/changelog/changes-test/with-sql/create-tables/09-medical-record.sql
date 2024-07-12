create table medicalrecord
(
    id            int auto_increment
        primary key,
    patient_id    int          null,
    doctor_id     int          null,
    diagnosis     varchar(255) null,
    treatment     varchar(255) null,
    document_path varchar(255) null,
    timestamp     datetime(6)  null,
    status        int          null,
    constraint FKmp3yuvputf4s2yb7f9428vna6
        foreign key (doctor_id) references doctor (id),
    constraint FKsvmdups3ln5phnc5qlvn93lmm
        foreign key (patient_id) references patient (id)
);

