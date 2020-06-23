package com.zensar.service.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zensar.service.library.entity.ServiceInstance;
import com.zensar.service.library.entity.ServiceLibrary;
import com.zensar.service.library.model.ServiceInstanceDto;
import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.repository.ServiceInstanceRepository;
import com.zensar.service.library.service.SamuraiServiceInstanceService;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceInstanceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private SamuraiServiceInstanceService mockInstanceService;

	@SpyBean
	private SamuraiServiceInstanceService spyInstanceService;

	@MockBean
	private ServiceInstanceRepository mockInstanceRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getServiceInstanceByName() throws Exception {
		when(mockInstanceService.getServiceInstanceByName("instance1")).thenReturn(getServiceInstanceDto());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/instance/instance1");
		MvcResult result = mvc.perform(requestBuilder).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		ServiceInstanceDto dto = objectMapper.readValue(contentAsString, ServiceInstanceDto.class);
		assertEquals(getServiceInstanceDto().getServiceInstanceId(), dto.getServiceInstanceId());
	}

	@Test
	public void getServiceInstanceByNameSpy() throws Exception {
		when(spyInstanceService.getServiceInstanceByName("instance1")).thenReturn(getServiceInstanceDto());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/instance/instance1");
		MvcResult result = mvc.perform(requestBuilder).andDo(print()).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		ServiceInstanceDto dto = objectMapper.readValue(contentAsString, ServiceInstanceDto.class);
		assertEquals(getServiceInstanceDto().getServiceInstanceId(), dto.getServiceInstanceId());
	}

	@Test
	public void saveServiceInstance() throws Exception {
		// Given
		ServiceInstanceDto dto = new ServiceInstanceDto(null, "instance1", null, null, null);
		// When
		when(mockInstanceService.createServiceInstance(ArgumentMatchers.any(dto.getClass()))).thenCallRealMethod()
				.thenReturn(getServiceInstanceDto());
		when(mockInstanceRepository.findByServiceInstanceName("instance1")).thenReturn(getServiceInstance());
		// Then
		mvc.perform(MockMvcRequestBuilders.post("/instance").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(dto)))
				.andExpect(jsonPath("$.serviceInstanceId").value("1"));
	}

	private ServiceInstanceDto getServiceInstanceDto() {
		ServiceLibraryDto library = new ServiceLibraryDto(null, null, "service1", "type1", false, "desc1",
				LocalDateTime.now(), 1L, null);
		ServiceInstanceDto dto = new ServiceInstanceDto(1L, "instance1", false, false, Arrays.asList(library));
		return dto;
	}

	private Optional<ServiceInstance> getServiceInstance() {
		ServiceLibrary library = new ServiceLibrary(11L, null, null, null, "service1", "type1", false, "desc1",
				LocalDateTime.now(), null);
		ServiceInstance instance = new ServiceInstance(1L, "instance1", false, false, Arrays.asList(library));
		return Optional.of(instance);
	}
}
