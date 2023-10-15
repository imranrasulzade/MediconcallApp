package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;

public interface ConnectionRequestService {
    Boolean sendConnectionRequest(ConnectionRequestDto connectionRequestDto);
}
