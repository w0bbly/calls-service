package com.example.callsservice.Repository;

import com.example.callsservice.Config.EntityMapper;
import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.Entity.Call;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CallMapping extends EntityMapper<CallDTO, Call> {
}
