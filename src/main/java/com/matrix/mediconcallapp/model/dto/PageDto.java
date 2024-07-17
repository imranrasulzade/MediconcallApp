package com.matrix.mediconcallapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto <T>{
    public Integer page;
    public Integer size;
    public Long totalElements;
    T data;
}
