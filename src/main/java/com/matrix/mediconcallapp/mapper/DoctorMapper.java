package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorProfileDto;
import com.matrix.mediconcallapp.model.dto.response.SimpleDoctorProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Date;


@Mapper(componentModel = "spring")
public abstract class DoctorMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.surname", target = "surname")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.info", target = "info")
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


    @Mapping(source = "userId", target = "user", qualifiedByName = "mapToUser")
    public abstract Doctor toDoctor(DoctorEditReqDto editReqDto);

    @Named(value = "mapToUser")
    public User mapToUser(Integer id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named(value = "mapToDateFormat")
    public String mapToDateFormat(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
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
