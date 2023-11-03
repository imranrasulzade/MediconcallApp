package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorProfileDto;
import com.matrix.mediconcallapp.model.dto.response.SimpleDoctorProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    @Mapping(source = "user", target = "userId", qualifiedByName = "mapToUserId")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user", target = "name", qualifiedByName = "mapToName")
    @Mapping(source = "user", target = "surname", qualifiedByName = "mapToSurname")
    @Mapping(source = "user", target = "phone", qualifiedByName = "mapToPhone")
    @Mapping(source = "user", target = "info", qualifiedByName = "mapToInfo")
    public abstract DoctorDto toDoctorDto(Doctor doctor);


    @Mapping(source = "status", target = "status", qualifiedByName = "mapToStatus")
    public abstract Doctor toDoctorForAdd(DoctorRegistrationRequestDto requestDto);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.surname", target = "surname")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.birthday", target = "birthday", qualifiedByName = "mapToDateFormat")
    @Mapping(source = "user.address", target = "address")
    @Mapping(source = "user.gender", target = "gender")
    @Mapping(source = "user.info", target = "info")
    @Mapping(source = "user.photoUrl", target = "photoUrl")
    public abstract DoctorProfileDto toDoctorProfileDto(Doctor doctor);


    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.surname", target = "surname")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.info", target = "info")
    @Mapping(source = "user.photoUrl", target = "photoUrl")
    public abstract SimpleDoctorProfileDto toSimpleDoctorProfileDto(Doctor doctor);







    @Named(value = "mapToName")
    public String mapToName(User user) {
        return user.getName();
    }

    @Named(value = "mapToSurname")
    public String mapToSurname(User user) {
        return user.getSurname();
    }

    @Named(value = "mapToPhone")
    public String mapToPhone(User user) {
        return user.getPhone();
    }

    @Named(value = "mapToInfo")
    public String mapToInfo(User user) {
        return user.getInfo();
    }

    @Named(value = "mapToUserId")
    public Integer mapToUserId(User user) {
        return user.getId();
    }


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
