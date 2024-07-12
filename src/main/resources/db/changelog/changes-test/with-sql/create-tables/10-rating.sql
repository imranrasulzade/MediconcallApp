create table rating
(
    id               int auto_increment
        primary key,
    rater_patient_id int          null,
    rated_doctor_id  int          null,
    rating_value     int          null,
    comment          varchar(255) null,
    timestamp        datetime(6)  null,
    status           int          null,
    constraint FKmdaioukhokxkdgbedygx8evp7
        foreign key (rater_patient_id) references patient (id),
    constraint FKoybjeft2su3t5uagauxxxmr9d
        foreign key (rated_doctor_id) references doctor (id),
    check (`rating_value` <= 5)
);

