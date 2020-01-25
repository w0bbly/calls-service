package com.example.callsservice.Service;

import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Repository.CallMapping;
import com.example.callsservice.Repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;
    @Autowired
    private CallMapping callMapping;

    public Page<CallDTO> getAllCalls(Call.Type type, Pageable pageable) {
        Page<Call> calls = callRepository.findAllByType(type, pageable);
        Page<CallDTO> callDTOS;

        callDTOS = calls.map(callObject -> {
            Optional<Call> call = callRepository.findById(callObject.getId());
            if(call.isPresent()) {
                return callMapping.toDto(callObject);
            } else {
                return null;
            }
        });

        return callDTOS;
    }

    public List<CallDTO> createCalls(List<CallDTO> callDTOS) {
        List<Call> calls;
        calls = callDTOS.stream().map(callDTO -> callMapping.toEntity(callDTO)).collect(Collectors.toList());
        return callMapping.toDto(calls);
    }

    public String deleteAllCalls() {
        callRepository.deleteAll();
        return "All calls deleted!";
    }

    public String deleteCallById(Long id) {
        Optional<Call> call = callRepository.findById(id);

        if(call.isPresent()) {
            callRepository.delete(call.get());
            return "Call with id -> " + id + "deleted!";
        } else
            return "Error while deleting call with id -> " + id;
    }
}
