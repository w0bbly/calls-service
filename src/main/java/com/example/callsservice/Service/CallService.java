package com.example.callsservice.Service;

import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.DTO.StatsDTO;
import com.example.callsservice.DTO.StatsHelperDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Repository.CallMapping;
import com.example.callsservice.Repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CallService {

    @Autowired
    private CallRepository callRepository;
    @Autowired
    private CallMapping callMapping;

    public Page<CallDTO> getAllCalls(Call.Type type, Pageable pageable) {
        Page<Call> calls;
        if (type == null) calls = callRepository.findAll(pageable);
        else calls = callRepository.findAllByType(type, pageable);
        Page<CallDTO> callDTOS;

        callDTOS = calls.map(callObject -> {
            Optional<Call> call = callRepository.findById(callObject.getId());
            if (call.isPresent()) {
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
        callRepository.saveAll(calls);
        return callMapping.toDto(calls);
    }

    public String deleteAllCalls() {
        callRepository.deleteAll();
        return "All calls deleted!";
    }

    public String deleteCallById(Long id) {
        Optional<Call> call = callRepository.findById(id);

        if (call.isPresent()) {
            callRepository.delete(call.get());
            return "Call with id -> " + id + "deleted!";
        } else
            return "Error while deleting call with id -> " + id;
    }

    public List<StatsDTO> getStatistics() {
        List<Call> calls = callRepository.findAll();
        List<StatsDTO> stats = new ArrayList<>();
        List<Call> callTemp;
        HashMap<Date, Integer> mapDates = new HashMap<>();
        HashMap<String, Integer> mapCallee = new HashMap<>();
        HashMap<String, Integer> mapCallers = new HashMap<>();

        calls.forEach(call -> {
            if(mapDates.isEmpty()) {
                mapDates.put(getZeroTimeDate(call.getStartTime()), 1);
            } else {
                if(!mapDates.containsKey(getZeroTimeDate(call.getStartTime())))
                    mapDates.put(getZeroTimeDate(call.getStartTime()), 1);
            }
        });

        mapDates.forEach((date, integer) -> stats.add(initialize(date)));

        for (int i = 0; i < stats.size(); i++) {
            AtomicLong diffInbound = new AtomicLong(0L);
            AtomicLong diffOutbound = new AtomicLong(0L);
            int finalI = i;

            callTemp = calls.stream().filter(call -> getZeroTimeDate(call.getStartTime()).equals(getZeroTimeDate(stats.get(finalI).getDateOfStats()))).collect(Collectors.toList());
            stats.get(finalI).setTotalCalls(callTemp.size());
            mapCallers.clear();
            mapCallee.clear();

            callTemp.forEach(call -> {
                if (call.getType().toString().equals(Call.Type.INBOUND.toString())) {
                    diffInbound.addAndGet((call.getEndTime().getTime() - call.getStartTime().getTime()));
                    int countHours = (int) diffInbound.get() / (60 * 60 * 1000);
                    int countMinutes;
                    if (countHours > 0) countMinutes = (int) diffInbound.get() / (60 * 1000) - (countHours * 60);
                    else countMinutes = (int) diffInbound.get() / (60 * 1000);
                    stats.get(finalI).setTotalCallTimeInbound(countHours + "h:" + countMinutes + "m");
                } else {
                    diffOutbound.addAndGet((call.getEndTime().getTime() - call.getStartTime().getTime()));
                    int countHours = (int) diffOutbound.get() / (60 * 60 * 1000);
                    int countMinutes;
                    if (countHours > 0) countMinutes = (int) diffOutbound.get() / (60 * 1000) - (countHours * 60);
                    else countMinutes = (int) diffOutbound.get() / (60 * 1000);
                    stats.get(finalI).setTotalCallTimeOutbound(countHours + "h:" + countMinutes + "m");
                }

                if (!mapCallee.isEmpty()) {
                    if (mapCallee.containsKey(call.getCalleeNumber())) {
                        mapCallee.put(call.getCalleeNumber(), mapCallee.get(call.getCalleeNumber()) + 1);
                    } else
                        mapCallee.put(call.getCalleeNumber(), 1);
                } else {
                    mapCallee.put(call.getCalleeNumber(), 1);
                }

                if (!mapCallers.isEmpty()) {
                    if (mapCallers.containsKey(call.getCallerNumber())) {
                        mapCallers.put(call.getCallerNumber(), mapCallers.get(call.getCallerNumber()) + 1);
                    } else
                        mapCallers.put(call.getCallerNumber(), 1);
                } else {
                    mapCallers.put(call.getCallerNumber(), 1);
                }

                if (call.getType().equals(Call.Type.OUTBOUND)) {
                    long callTimeInMinutes = (call.getEndTime().getTime() - call.getStartTime().getTime()) / (60 * 1000);
                    double callCost;
                    if (callTimeInMinutes > 5) {
                        double callCostFirstFive = 5 * 0.10;
                        double callCostAfterFive = (callTimeInMinutes - 5) * 0.05;
                        callCost = callCostAfterFive + callCostFirstFive;
                    } else {
                        callCost = callTimeInMinutes * 0.10;
                    }
                    stats.get(finalI).setTotalCallCost(stats.get(finalI).getTotalCallCost() + callCost);
                }
            });

            mapCallee.forEach((s, integer) -> {
                StatsHelperDTO statsHelperDTO = new StatsHelperDTO();
                statsHelperDTO.setNumber(s);
                statsHelperDTO.setNumberOfCalls(integer);
                stats.get(finalI).getNumberOfCallsByCalleeNumber().add(statsHelperDTO);
            });

            mapCallers.forEach((s, integer) -> {
                StatsHelperDTO statsHelperDTO = new StatsHelperDTO();
                statsHelperDTO.setNumber(s);
                statsHelperDTO.setNumberOfCalls(integer);
                stats.get(finalI).getNumberOfCallsByCallerNumber().add(statsHelperDTO);
            });
        }

        return stats;
    }

    private StatsDTO initialize(Date date) {
        StatsDTO statsDTO = new StatsDTO();
        statsDTO.setDateOfStats(date);
        statsDTO.setTotalCalls(0);
        statsDTO.setTotalCallTimeInbound("");
        statsDTO.setNumberOfCallsByCalleeNumber(new ArrayList<>());
        statsDTO.setNumberOfCallsByCallerNumber(new ArrayList<>());
        statsDTO.setTotalCallCost(0);
        statsDTO.setTotalCallTimeOutbound("");
        return statsDTO;
    }

    private static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }
}
