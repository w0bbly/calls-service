package com.example.callsservice.Controller;

import com.example.callsservice.Config.ResponseMessage;
import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.DTO.StatsDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Service.CallService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/calls")
public class CallsController {

    private final CallService callService;

    public CallsController(CallService callService) {
        this.callService = callService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Page<CallDTO>> getAllCalls(@RequestParam(required = false) Call.Type type, Pageable pageable) {
        Page<CallDTO> callDTO = callService.getAllCalls(type, pageable);
        return ResponseEntity.ok(callDTO);
    }

    @PostMapping
    public ResponseEntity<List<CallDTO>> createCall(@RequestBody @Valid List<CallDTO> calls) {
        return ResponseEntity.ok(callService.createCalls(calls));
    }

    @GetMapping(path = "/statistics")
    public ResponseEntity<List<StatsDTO>> getStatistics() {
        return ResponseEntity.ok(callService.getStatistics());
    }

    @DeleteMapping(path = "/all")
    public ResponseEntity<ResponseMessage> deleteAllCalls() { return ResponseEntity.ok(callService.deleteAllCalls()); }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ResponseMessage> deleteCallById(@PathVariable Long id) {
        ResponseMessage response = callService.deleteCallById(id);
        if (response.getMessage().contains("Error")) {
            return ResponseEntity.status(401).body(response);
        } else return ResponseEntity.ok(response);
    }
}
