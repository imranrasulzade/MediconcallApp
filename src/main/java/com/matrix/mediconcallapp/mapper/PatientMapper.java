package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.service.utility.MapPathUtility;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Mapper(componentModel = "spring")
@Slf4j
public abstract class PatientMapper {
//    @Value("${application.files.directory}")
    private static String UPLOAD_DIR = "C:\\Users\\imran\\Documents";

    @Mapping(source = "document", target = "documentPath", qualifiedByName = "mapToDocumentPath")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapToStatus")
    public abstract Patient toPatientForAdd(PatientRegistrationRequestDto requestDto);

    @Mapping(source = "user", target = "name", qualifiedByName = "mapToName")
    @Mapping(source = "user", target = "surname", qualifiedByName = "mapToSurname")
    @Mapping(source = "user", target = "phone", qualifiedByName = "mapToPhone")
    @Mapping(source = "user", target = "info", qualifiedByName = "mapToInfo")
    public abstract PatientDto toPatientDto(Patient patient);

    @Named(value = "mapToDocumentPath")
    public String mapToDocumentPath(MultipartFile document){
        return MapPathUtility.mapPath(document, UPLOAD_DIR);
    }


    @Named("mapToName")
    public String mapToName(User user){
        return user.getName();
    }

    @Named(value = "mapToSurname")
    public String mapToSurname(User user){
        return user.getSurname();
    }

    @Named(value = "mapToPhone")
    public String mapToPhone(User user){
        return user.getPhone();
    }

    @Named(value = "mapToInfo")
    public String mapToInfo(User user){
        return user.getInfo();
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