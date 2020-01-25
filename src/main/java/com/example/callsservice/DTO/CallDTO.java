package com.example.callsservice.DTO;

import com.example.callsservice.Entity.Call;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class CallDTO {
    private Long id;
    private String callerNumber;
    private String calleeNumber;
    private Timestamp startTimestamp;
    private Timestamp endTimestamp;
    private Call.Type type;
}
