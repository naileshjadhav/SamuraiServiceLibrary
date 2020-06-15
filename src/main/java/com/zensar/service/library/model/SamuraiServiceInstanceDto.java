package com.zensar.service.library.model;

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
public class SamuraiServiceInstanceDto {

	private Long serviceInstanceId;
	private String serviceInstanceName;
	private Boolean isDecomissioned;
	private Boolean isInActive;
	private ServiceLibraryDto serviceLibrary;
	private String serviceName;

}
