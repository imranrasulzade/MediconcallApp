package com.matrix.mediconcallapp.model.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeDto {
    private Integer day;
    private Integer month;
    private Integer hour;
    private Integer status;
}
