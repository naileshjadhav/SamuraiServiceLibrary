package com.zensar.service.library;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.service.LibraryService;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceLibraryControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private LibraryService libraryService;

	private ServiceLibraryDto getServiceLibraryDto() {
		SuperCategoryDto superCategory = new SuperCategoryDto(1L, "super1", true, LocalDateTime.now(), null, null);
		SubCategoryDto subCategory = new SubCategoryDto(1L, "sub1", true, LocalDateTime.now(), superCategory, null);
		ServiceLibraryDto value = new ServiceLibraryDto(superCategory, subCategory, "V2", "Type1", false, "desc1",
				LocalDateTime.now(), 1L, null);
		return value;
	}

	@Test
	public void getServiceLibraryById() throws Exception {
		when(libraryService.getServiceLibraryById(1L)).thenReturn(getServiceLibraryDto());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/library/1");
		MvcResult result = mvc.perform(requestBuilder).andDo(print())
				.andExpect(jsonPath("$.superCategory.superCategoryName", is("super1"))).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void getServiceLibraryByIdUsingModelDto() throws Exception {
		when(libraryService.getServiceLibraryById(1L)).thenReturn(getServiceLibraryDto());
		MvcResult result = mvc.perform(get("/library/1")).andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		ServiceLibraryDto dto = objectMapper.readValue(contentAsString, ServiceLibraryDto.class);
		assertEquals(getServiceLibraryDto().getServiceId(), dto.getServiceId());
	}

//	@Test
//	public void getEnabledServiceLibraryByName() {
//
//	}
//
//	@Test
//	public void getEnabledServiceLibraryBySubCategoryId() {
//
//	}
//
//	@Test
//	public void getAllEnabledServiceLibrary() {
//
//	}
//
//	@Test
//	public void createServiceLibrary() {
//
//	}
//
//	@Test
//	public void updateServiceLibrary() {
//
//	}

}
