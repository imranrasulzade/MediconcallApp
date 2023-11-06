package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.mapper.mappingUtil.MapPathUtility;
import com.matrix.mediconcallapp.model.dto.request.PatientEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Mapper(componentModel = "spring")
@Slf4j
public abstract class PatientMapper {
    @Value("${application.files.directory}")
    private String UPLOAD_DIR;

    @Mapping(source = "document", target = "documentPath", qualifiedByName = "mapToDocumentPath")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapToStatus")
    public abstract Patient toPatientForAdd(PatientRegistrationRequestDto requestDto);


    @Mapping(source = "document", target = "documentPath", qualifiedByName = "mapToDocumentPath")
    @Mapping(source = "userId", target = "user", qualifiedByName = "mapToUser")
    public abstract Patient toPatient(PatientEditReqDto editReqDto);

    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.surname", target = "surname")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "user.info", target = "info")
    public abstract PatientDto toPatientDto(Patient patient);


    @Named(value = "mapToUser")
    public User mapToUser(Integer id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named(value = "mapToDocumentPath")
    public String mapToDocumentPath(MultipartFile document){
        return MapPathUtility.mapPath(document, this.UPLOAD_DIR);
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