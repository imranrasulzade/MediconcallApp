package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")

public abstract class DoctorMapper {
    private RatingRepository ratingRepository;


    @Mapping(source = "user", target = "userId", qualifiedByName = "mapToUserId")
    @Mapping(source = "id", target = "doctorId")
    @Mapping(source = "user", target = "name", qualifiedByName = "mapToName")
    @Mapping(source = "user", target = "surname", qualifiedByName = "mapToSurname")
    @Mapping(source = "user", target = "phone", qualifiedByName = "mapToPhone")
    @Mapping(source = "user", target = "info", qualifiedByName = "mapToInfo")
    public abstract DoctorDto toDoctorDto(Doctor doctor);

    public abstract Doctor toDoctor(DoctorDto doctorDto);


    @Mapping(source = "status", target = "status", qualifiedByName = "mapToStatus")
    public abstract Doctor toDoctorForAdd(DoctorRegistrationRequestDto requestDto);


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
