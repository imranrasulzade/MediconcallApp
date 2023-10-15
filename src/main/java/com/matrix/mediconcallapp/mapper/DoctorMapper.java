package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.model.UserStatus;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

//    @Mapping(source = "user", target = "name", qualifiedByName = "mapToName")
//    @Mapping(source = "user", target = "surname", qualifiedByName = "mapToSurname")
//    @Mapping(source = "user", target = "phone", qualifiedByName = "mapToPhone")
//    @Mapping(source = "user", target = "info", qualifiedByName = "mapToInfo")
    public abstract DoctorDto toDoctorDto(Doctor doctor);

    public abstract Doctor toDoctor(DoctorDto doctorDto);


    @Mapping(source = "status", target = "status", qualifiedByName = "mapToStatus")
    public abstract Doctor toDoctorForAdd(DoctorRegistrationRequestDto requestDto);


//    @Named("mapToName")
//    public String mapToName(Doctor doctor) {
//        return doctor.getUser().getName();
//    }
//
//    @Named("mapToSurname")
//    public String mapToSurname(Doctor doctor) {
//        return doctor.getUser().getSurname();
//    }
//
//    @Named("mapToPhone")
//    public String mapToPhone(Doctor doctor) {
//        return doctor.getUser().getPhone();
//    }
//
//    @Named("mapToInfo")
//    public String mapToInfo(Doctor doctor) {
//        return doctor.getUser().getInfo();
//    }

    @Named(value = "mapToStatus")
    public Integer mapToStatus(UserStatus status){
        int s;
        if(status == UserStatus.ACTIVE){
            s = 1;
        }else if(status == UserStatus.INACTIVE){
            s = 0;
        }else {
            s = -1;
        }
        return s;
    }

}
