package com.example.callsservice.DTO;

import com.example.callsservice.Entity.Call;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@ToString
public class CallDTO {
    @NotNull
    private String callerNumber;
    @NotNull
    private String calleeNumber;
    @NotNull
    private Date startTime;
    @NotNull
    private Date endTime;
    @NotNull
    private Call.Type type;
}
