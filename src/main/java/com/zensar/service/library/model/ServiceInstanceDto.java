package com.zensar.service.library.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
public class ServiceInstanceDto {

	private Long serviceInstanceId;
	private String serviceInstanceName;
	private Boolean isDecomissioned;
	private Boolean isInActive;
	private List<ServiceLibraryDto> serviceLibrary = new ArrayList<ServiceLibraryDto>();

	public boolean addServiceLibraryDto(ServiceLibraryDto dto) {
		return this.serviceLibrary.add(dto);
		// return this.serviceLibrary;
	}
	// private ServiceLibraryDto serviceLibrary;
	// private String serviceName;

}
