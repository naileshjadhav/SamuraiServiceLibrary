package com.zensar.service.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.entity.ServiceLibrary;
import com.zensar.service.library.entity.SubCategory;
import com.zensar.service.library.entity.SuperCategory;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.repository.SubCategoryRespository;
import com.zensar.service.library.repository.SuperCategoryRespository;
import com.zensar.service.library.util.BeanUtilToCopyNonNullProperties;

@Service
public class SubCategoryService {

	private static final Logger log = LoggerFactory.getLogger(SubCategoryService.class);

	@Autowired
	private SubCategoryRespository repository;
	@Autowired
	private SuperCategoryRespository superCategoryRespository;

	public SubCategoryDto findSubCategoryByName(String subCategoryName) {

		SubCategory subCategory = repository.findBysubCategoryName(subCategoryName);
		SubCategoryDto target = new SubCategoryDto();
		BeanUtils.copyProperties(subCategory, target);
		return target;
	}

	public SubCategoryDto getSubcategoryById(Long id) {
		SubCategory category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for sub categoryId::" + id));
		SubCategoryDto dto = new SubCategoryDto();
		List<ServiceLibrary> libraries = category.getLibraries();
		List<ServiceLibraryDto> librariesDto = libraries.stream()
				.map(e -> new ServiceLibraryDto(null, null, e.getServiceName(), e.getTypeOfService(),
						e.isServiceDecommisioned(), e.getServiceDescription(), e.getCreationDate(), e.getServiceId(),
						null))
				.collect(Collectors.toList());
		BeanUtils.copyProperties(category, dto);
		dto.setLibrary(librariesDto);
		return dto;
	}

	public SubCategoryDto saveSubCategory(SubCategoryDto dto) {

		SubCategory entity = new SubCategory();
		BeanUtils.copyProperties(dto, entity);
		entity = repository.save(entity);
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	public List<SubCategoryDto> getAllSubCategory() {
		List<SubCategoryDto> target = new ArrayList<SubCategoryDto>();
		List<SubCategory> list = (List<SubCategory>) repository.findAll();
		SuperCategoryDto superCategoryDto = new SuperCategoryDto();
		for (SubCategory subCategory : list) {
			BeanUtils.copyProperties(subCategory.getSuperCategory(), superCategoryDto);
		}
		target = list
				.stream().map(e -> new SubCategoryDto(e.getSubCategoryId(), e.getSubCategoryName(),
						e.isSubCategoryEnable(), e.getCreationDate(), superCategoryDto, null))
				.collect(Collectors.toList());
		return target;
	}

	public List<SubCategoryDto> getSubcategoryBySuperCategoryId(Long id) {

		SuperCategory superCategory = superCategoryRespository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Resource Not found for id: " + id));
		List<SubCategory> subCategories = superCategory.getSubCategory();
		List<SubCategoryDto> target = new ArrayList<SubCategoryDto>();
		SuperCategoryDto superCategoryDto = new SuperCategoryDto();
		BeanUtils.copyProperties(superCategory, superCategoryDto);
		log.info("SuperCategoryName: " + superCategoryDto.getSuperCategoryName());
		target = subCategories
				.stream().map(e -> new SubCategoryDto(e.getSubCategoryId(), e.getSubCategoryName(),
						e.isSubCategoryEnable(), e.getCreationDate(), superCategoryDto, null))
				.collect(Collectors.toList());
		return target;
	}

	public SubCategoryDto updateSubCategory(SubCategoryDto dto) {
		Long id = dto.getSubCategoryId();
		SubCategory subCategory = repository.findById(dto.getSubCategoryId())
				.orElseThrow(() -> new ResourceNotFound("Resource not found for sub category id: " + id));
		SubCategoryDto target = new SubCategoryDto();
		BeanUtils.copyProperties(subCategory, target);
		BeanUtilToCopyNonNullProperties<SubCategoryDto> util = new BeanUtilToCopyNonNullProperties<SubCategoryDto>();
		SubCategoryDto valueObj = util.copyNonNullProperties(target, dto);
		BeanUtils.copyProperties(valueObj, subCategory);
		subCategory = repository.save(subCategory);
		BeanUtils.copyProperties(subCategory, dto);
		return dto;
	}

}
