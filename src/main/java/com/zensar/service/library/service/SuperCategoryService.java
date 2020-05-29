package com.zensar.service.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.entity.SuperCategory;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.repository.SuperCategoryRespository;

@Service
public class SuperCategoryService {

	@Autowired
	private SuperCategoryRespository repository;

	public SuperCategoryDto getSuperCategoryById(Long id) {
		SuperCategory category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for super categoryId::" + id));
		SuperCategoryDto dto = new SuperCategoryDto();
		BeanUtils.copyProperties(category, dto);
		return dto;
	}

	public SuperCategoryDto saveSuperCategory(SuperCategoryDto dto) {
		SuperCategory entity = new SuperCategory();
		BeanUtils.copyProperties(dto, entity);
//		List<ServiceLibraryDto> libraryDtos = dto.getLibraries();
//		for (ServiceLibraryDto serviceLibraryDto : libraryDtos) {
//			ServiceLibrary library = new ServiceLibrary();
//			BeanUtils.copyProperties(serviceLibraryDto, library);
//			entity.addLibrary(library);
//		}
		// ServiceLibrary(Long serviceId, SuperCategory superCategory, SubCategory
		// subCategory, String serviceName, String typeOfService, boolean
		// serviceDecommisioned, String serviceDescription, LocalDateTime creationDate)
//		List<ServiceLibrary> libraries = dto.getLibraries().stream()
//				.map(e -> new ServiceLibrary(null, null, null, e.getServiceName(), e.getTypeOfService(),
//						e.isServiceDecommisioned(), e.getServiceDescription(), e.getCreationDate(), e.getLogoImage()))
//				.collect(Collectors.toList());
//		entity.setLibraries(libraries);
		// Long subCategoryId, String subCategoryName, boolean subCategoryEnable,
		// LocalDateTime creationDate, List<SuperCategory> superCategory,
		// List<ServiceLibrary> libraries)
//		List<SubCategory> subCategories = dto.getSubCategories().stream()
//				.map(e -> new SubCategory(null,e.getSubCategoryName(),e.isSubCategoryEnable(),e.getCreationDate(),null,null))
//				.collect(Collectors.toList());
//		entity.setSubCategory(subCategories);
//		entity.setLibraries(libraries);

//		List<SubCategoryDto> subCategoryDtos = dto.getSubCategories();
//		for (SubCategoryDto subCategoryDto : subCategoryDtos) {
//			SubCategory subCategory = new SubCategory();
//			BeanUtils.copyProperties(subCategoryDto, subCategory);
//			entity.addSubCategory(subCategory);
//		}
		entity = repository.save(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public List<SuperCategoryDto> getAllSuperCategory() {
		List<SuperCategoryDto> target = new ArrayList<SuperCategoryDto>();
		List<SuperCategory> list = (List<SuperCategory>) repository.findAll();
		target = list.stream().map(e -> new SuperCategoryDto(e.getSuperCategoryId(), e.getSuperCategoryName(),
				e.isSuperCategoryEnable(), e.getCreationDate(), null, null)).collect(Collectors.toList());
		return target;
	}

}
