package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.service.utility.MapPathUtility;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Mapper(componentModel = "spring")
public abstract class UserMapper {


//    @Value("${application.files.directory}")
    private static String UPLOAD_DIR = "C:\\Users\\imran\\Documents";

    public abstract UserDto toUserDto(User user);

    @Mapping(source = "photo", target = "photoUrl", qualifiedByName = "mapPath")
    public abstract User toUserForAddDoctor(DoctorRegistrationRequestDto requestDto);

    @Mapping(source = "photo", target = "photoUrl", qualifiedByName = "mapPath")
    public abstract User toUserForAddPatient(PatientRegistrationRequestDto requestDto);



    @Named(value = "mapPath")
    public String mapPath(MultipartFile photo) {
        return MapPathUtility.mapPath(photo, UPLOAD_DIR);
    }

}
