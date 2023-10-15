package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Request;
import com.matrix.mediconcallapp.mapper.ConnectionMapper;
import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import com.matrix.mediconcallapp.repository.RequestRepository;
import com.matrix.mediconcallapp.service.ConnectionRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionRequestServiceImpl implements ConnectionRequestService {
    private final RequestRepository requestRepository;
    private final ConnectionMapper connectionMapper;

    @Override
    public Boolean sendConnectionRequest(ConnectionRequestDto connectionRequestDto) {
        Request request = connectionMapper.toRequest(connectionRequestDto);
        Request r = requestRepository.save(request);
        return r.getId() != null;
    }
}
