package com.example.callsservice;

import com.example.callsservice.DTO.CallDTO;
import com.example.callsservice.Entity.Call;
import com.example.callsservice.Repository.CallMapping;
import com.example.callsservice.Repository.CallMappingImpl;
import com.example.callsservice.Repository.CallRepository;
import com.example.callsservice.Service.CallService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CallMappingImpl.class})
public class TestServiceResponse {

	@MockBean
	private CallRepository callRepository;
	@Autowired
	private CallMapping callMapping;
	private CallService callService;
	private Call call;

	@Before
	public void setUp() {
		callService = new CallService(callRepository, callMapping);
		this.call = new Call(1L, "914248088", "914248089", new Date(), new Date(), Call.Type.INBOUND, 0.0);
		Page<Call> calls = new PageImpl<>(Collections.singletonList(call));
		when(callRepository.save(call)).thenReturn(call);
		when(callRepository.findAllByType(Call.Type.INBOUND, PageRequest.of(0, 10))).thenReturn(calls);
		when(callRepository.findAll(PageRequest.of(0, 10))).thenReturn(calls);
	}

	@Test
	public void createCall() {
		CallDTO callDTO = new CallDTO(1L, "914248088", "914248089", new Date(), new Date(), Call.Type.INBOUND);
		assertThat(callService.createCalls(Collections.singletonList(callDTO))).size().isEqualTo(1);
	}

	@Test
	public void getAllCallsInbound() {
		assertThat(callService.getAllCalls(Call.Type.INBOUND, PageRequest.of(0, 10)).getTotalElements()).isEqualTo(1);
	}

	@Test
	public void getAllCallsNoType() {
		assertThat(callService.getAllCalls(null, PageRequest.of(0, 10)).getTotalElements()).isEqualTo(1);
	}

	@Test
	public void deleteAllCalls() {
		assertThat(callService.deleteAllCalls().getMessage()).isEqualTo("All calls deleted!");
	}

	@Test
	public void deleteCallById() {
		callService.deleteCallById(call.getId());
		verify(callRepository, times(1)).findById(this.call.getId());
	}
}
