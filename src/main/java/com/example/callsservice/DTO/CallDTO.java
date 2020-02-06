package com.example.callsservice.DTO;

import com.example.callsservice.Entity.Call;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CallDTO {
    private Long id;
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
