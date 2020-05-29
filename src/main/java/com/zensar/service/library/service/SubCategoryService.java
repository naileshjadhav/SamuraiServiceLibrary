package com.zensar.service.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.entity.SubCategory;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.repository.SubCategoryRespository;

@Service
public class SubCategoryService {

	@Autowired
	private SubCategoryRespository repository;

	public SubCategoryDto getSubcategoryById(Long id) {
		SubCategory category = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for sub categoryId::" + id));
		SubCategoryDto dto = new SubCategoryDto();
		BeanUtils.copyProperties(category, dto);
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
		target = list.stream().map(e -> new SubCategoryDto(e.getSubCategoryId(), e.getSubCategoryName(),
				e.isSubCategoryEnable(), e.getCreationDate(), null, null)).collect(Collectors.toList());
		return target;
	}

}
