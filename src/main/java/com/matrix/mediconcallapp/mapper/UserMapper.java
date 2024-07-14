package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.mapper.mappingUtil.MapPathUtility;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class UserMapper {


    @Value("${application.files.directory}")
    private String UPLOAD_DIR;

    @Mapping(source = "birthday", target = "birthday", qualifiedByName = "mapToDate")
    @Mapping(source = "authorities", target = "role", qualifiedByName = "mapToRole")
    public abstract UserDto toUserDto(User user);

    @Mapping(source = "photo", target = "photoUrl", qualifiedByName = "mapPath")
    public abstract User toUser(UserEditReqDto userEditReqDto);

    @Mapping(source = "photo", target = "photoUrl", qualifiedByName = "mapPath")
    public abstract User toUserForAddDoctor(DoctorRegistrationRequestDto requestDto);

    @Mapping(source = "photo", target = "photoUrl", qualifiedByName = "mapPath")
    public abstract User toUserForAddPatient(PatientRegistrationRequestDto requestDto);

    @Mapping(source = "doctor.academicTitle", target = "academicTitle")
    @Mapping(source = "doctor.specialty", target = "specialty")
    @Mapping(source = "doctor.placeOfWork", target = "placeOfWork")
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "doctor.id", target = "doctorId")
    public abstract DoctorForListProfileDto toDoctorForListProfileDto(User user);


    @Named(value = "mapPath")
    public String mapPath(MultipartFile photo) {
        return MapPathUtility.mapPath(photo, this.UPLOAD_DIR);
    }

    @Named(value = "mapToDate")
    public String mapToDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    @Named(value = "mapToRole")
    public String mapToRole(Set<Authority> authorities){
        for (Authority value : authorities) {
            return value.getName();
        }
        return null;
    }
}
