package com.example.callsservice.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class StatsDTO {
    private Date dateOfStats;
    private String totalCallTimeInbound;
    private String totalCallTimeOutbound;
    private int totalCalls;
    private List<StatsHelperDTO> numberOfCallsByCallerNumber;
    private List<StatsHelperDTO> numberOfCallsByCalleeNumber;
    private double totalCallCost;
}
