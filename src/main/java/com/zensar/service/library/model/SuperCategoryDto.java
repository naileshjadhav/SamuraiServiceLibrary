package com.zensar.service.library.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SuperCategoryDto {

	private Long superCategoryId;
	private String superCategoryName;
	private boolean superCategoryEnable;
	private LocalDateTime creationDate=LocalDateTime.now();
	private List<SubCategoryDto> subCategories = new ArrayList<SubCategoryDto>();
	@JsonIgnore
	private List<ServiceLibraryDto> libraries = new ArrayList<ServiceLibraryDto>();

}
