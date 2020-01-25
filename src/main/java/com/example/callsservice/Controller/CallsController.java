package com.example.callsservice.Controller;

import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/calls")
public class CallsController {

    @Autowired
    private CallService callService;

    @GetMapping(path = "/all")
    public ResponseEntity getAllCalls(@RequestParam Call.Type type, Pageable pageable) {
        Page<CallDTO> callDTO = callService.getAllCalls(type, pageable);
        return ResponseEntity.ok(callDTO);
    }
}
