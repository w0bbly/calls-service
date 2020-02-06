package com.example.callsservice;

import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Repository.CallMapping;
import com.example.callsservice.Repository.CallMappingImpl;
import com.example.callsservice.Repository.CallRepository;
import com.example.callsservice.Service.CallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {CallMappingImpl.class})
class CallsServiceApplicationTests {

	private CallRepository callRepository = Mockito.mock(CallRepository.class);

	@Autowired
	private CallMapping callMapping;

	private CallService callService;

	@BeforeEach
	void initCallService() {
		callService = new CallService(callRepository, callMapping);
	}

	@Test
	void createCalls() {
		List<CallDTO> callDTOS = new ArrayList<>();
		List<CallDTO> response;
		CallDTO callDTO = new CallDTO();
		callDTO.setCalleeNumber("914248088");
		callDTO.setCallerNumber("914248089");
		callDTO.setStartTime(new Date());
		callDTO.setEndTime(new Date(new Date().getTime() + (10 * 60000)));
		callDTO.setType(Call.Type.INBOUND);

		callDTOS.add(callDTO);

		response = callService.createCalls(callDTOS);

		assertThat(response.get(0).getCallerNumber()).isEqualTo("914248089");
	}

	@Test
	void getAllCalls() {
		Page<CallDTO> callDTOS = callService.getAllCalls(Call.Type.INBOUND, PageRequest.of(0, 10));
		assertThat(callDTOS.getTotalElements()).isEqualTo(1);
	}

	@Test
	void deleteCallById() {
	}

	@Test
	void getStatistics() {

	}
}
