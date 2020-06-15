package com.zensar.service.library.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class SubCategoryDto {

	private Long subCategoryId;
	private String subCategoryName;
	private boolean subCategoryEnable;
	private LocalDateTime creationDate=LocalDateTime.now();
	private SuperCategoryDto superCategories;
	private List<ServiceLibraryDto> library = new ArrayList<ServiceLibraryDto>();
}
