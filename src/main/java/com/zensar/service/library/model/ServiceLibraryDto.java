package com.zensar.service.library.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString(exclude = "logoImage")
public class ServiceLibraryDto {

	//private ServiceInstanceDto instanceDto;
	private SuperCategoryDto superCategory;
	private SubCategoryDto subCategory;
	private String serviceName;
	private String typeOfService;
	private boolean serviceDecommisioned;
	private String serviceDescription;
	private LocalDateTime creationDate = LocalDateTime.now();
	private Long serviceId;
	private byte[] logoImage;
}
