package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

@Data
public class TimeDto {
    private Integer doctorId;
    private String doctorName;
    private Integer day;
    private Integer month;
    private Integer hour;
    private Integer status;
}
