package com.zensar.service.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.service.library.controller.BeanUtilToCopyNonNullProperties;
import com.zensar.service.library.entity.ServiceLibrary;
import com.zensar.service.library.entity.SubCategory;
import com.zensar.service.library.entity.SuperCategory;
import com.zensar.service.library.exception.ResourceNotFound;
import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.repository.ServiceLibraryRespository;
import com.zensar.service.library.repository.SubCategoryRespository;
import com.zensar.service.library.repository.SuperCategoryRespository;

@Service
public class LibraryService {

	@Autowired
	private ServiceLibraryRespository repository;
	@Autowired
	SuperCategoryRespository superCategoryRespository;
	@Autowired
	SubCategoryRespository subCategoryRespository;

	private static final Logger log = LoggerFactory.getLogger(LibraryService.class);

	public ServiceLibraryDto getServiceLibraryById(Long id) {
		ServiceLibrary library = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for serviceId::" + id));
		ServiceLibraryDto dto = new ServiceLibraryDto();
		SuperCategoryDto superDto = null;
		SubCategoryDto subDto = null;
		if (library.getSuperCategory() != null) {
			superDto = new SuperCategoryDto();
			BeanUtils.copyProperties(library.getSuperCategory(), superDto);
		}
		if (library.getSubCategory() != null) {
			subDto = new SubCategoryDto();
			BeanUtils.copyProperties(library.getSubCategory(), subDto);
		}
		dto.setSuperCategory(superDto);
		dto.setSubCategory(subDto);
		BeanUtils.copyProperties(library, dto);
		return dto;
	}

	public ServiceLibraryDto saveServiceLibrary(ServiceLibraryDto dto) {

		ServiceLibrary entity = new ServiceLibrary();
		BeanUtils.copyProperties(dto, entity);
		SuperCategory superCategoryEntity = new SuperCategory();
		BeanUtils.copyProperties(dto.getSuperCategory(), superCategoryEntity);
		entity.setSuperCategory(superCategoryEntity);
		log.info("sub category Name::" + dto.getSubCategory().getSubCategoryName());
		SubCategory subCategory = new SubCategory();
		BeanUtils.copyProperties(dto.getSubCategory(), subCategory);
		entity.setSubCategory(subCategory);
		entity = repository.save(entity);
		log.info("SubCategoryId: " + entity.getSubCategory().getSubCategoryId());
		BeanUtils.copyProperties(entity, dto);
		log.info("Saved library" + dto.getServiceId());
		return dto;
	}

	public List<ServiceLibraryDto> getServiceLibrary() {
		List<ServiceLibraryDto> target = new ArrayList<ServiceLibraryDto>();
		List<ServiceLibrary> libraries = (List<ServiceLibrary>) repository.findAll();

		for (ServiceLibrary serviceLibrary : libraries) {
			SuperCategoryDto superCategoryDto = new SuperCategoryDto();
			if (serviceLibrary.getSuperCategory() != null)
				BeanUtils.copyProperties(serviceLibrary.getSuperCategory(), superCategoryDto);
			SubCategoryDto subCategoryDto = new SubCategoryDto();
			if (serviceLibrary != null && serviceLibrary.getSubCategory() != null)
				BeanUtils.copyProperties(serviceLibrary.getSubCategory(), subCategoryDto);
			target.add(new ServiceLibraryDto(superCategoryDto, subCategoryDto, serviceLibrary.getServiceName(),
					serviceLibrary.getTypeOfService(), serviceLibrary.isServiceDecommisioned(),
					serviceLibrary.getServiceDescription(), serviceLibrary.getCreationDate(),
					serviceLibrary.getServiceId(), serviceLibrary.getLogoImage()));

		}
		return target;
	}

	public ServiceLibraryDto updateService(ServiceLibraryDto dto) {

		ServiceLibrary library = repository.findById(dto.getServiceId())
				.orElseThrow(() -> new ResourceNotFound("Resource not found for id: " + dto.getServiceId()));
		ServiceLibraryDto target = new ServiceLibraryDto();
		BeanUtils.copyProperties(library, target);
		BeanUtilToCopyNonNullProperties<ServiceLibraryDto> util = new BeanUtilToCopyNonNullProperties<ServiceLibraryDto>();
		ServiceLibraryDto valueObj = util.copyNonNullProperties(target, dto);
		BeanUtils.copyProperties(valueObj, library);
		log.info("Entity to save: {}", library.toString());
		library = repository.save(library);
		SuperCategoryDto superDto = null;
		SubCategoryDto subDto = null;
		if (library.getSuperCategory() != null) {
			superDto = new SuperCategoryDto();
			BeanUtils.copyProperties(library.getSuperCategory(), superDto);
		}
		if (library.getSubCategory() != null) {
			subDto = new SubCategoryDto();
			BeanUtils.copyProperties(library.getSubCategory(), subDto);
		}
		dto.setSuperCategory(superDto);
		dto.setSubCategory(subDto);
		BeanUtils.copyProperties(library, dto);
		return dto;
	}

	public List<ServiceLibraryDto> getServiceByName(String name) {
		List<ServiceLibraryDto> target = new ArrayList<ServiceLibraryDto>();
		List<ServiceLibrary> libraries = repository.findAllByServiceName(name)
				.orElseThrow(() -> new ResourceNotFound("Resource not found for name: " + name));

		for (ServiceLibrary serviceLibrary : libraries) {
			SuperCategoryDto superCategoryDto = new SuperCategoryDto();
			if (serviceLibrary.getSuperCategory() != null)
				BeanUtils.copyProperties(serviceLibrary.getSuperCategory(), superCategoryDto);
			SubCategoryDto subCategoryDto = new SubCategoryDto();
			if (serviceLibrary != null && serviceLibrary.getSubCategory() != null)
				BeanUtils.copyProperties(serviceLibrary.getSubCategory(), subCategoryDto);
			target.add(new ServiceLibraryDto(superCategoryDto, subCategoryDto, serviceLibrary.getServiceName(),
					serviceLibrary.getTypeOfService(), serviceLibrary.isServiceDecommisioned(),
					serviceLibrary.getServiceDescription(), serviceLibrary.getCreationDate(),
					serviceLibrary.getServiceId(), serviceLibrary.getLogoImage()));

		}
		return target;
	}

	public List<ServiceLibraryDto> getEnabledServicesBySubCategoryId(Long subCategoryId) {
		SubCategory subCategory = subCategoryRespository.findById(subCategoryId)
				.orElseThrow(() -> new ResourceNotFound("Resource not found id: " + subCategoryId));
		Predicate<? super ServiceLibrary> predicate = e -> e.isServiceDecommisioned() == false;
		List<ServiceLibrary> libraries = subCategory.getLibraries().stream().filter(predicate)
				.collect(Collectors.toList());
		SuperCategoryDto superCategoryDto = new SuperCategoryDto();
		BeanUtils.copyProperties(subCategory.getSuperCategory(), superCategoryDto);
		SubCategoryDto subCategoryDto = new SubCategoryDto();
		BeanUtils.copyProperties(subCategory, subCategoryDto);
		List<ServiceLibraryDto> target = libraries.stream()
				.map(e -> new ServiceLibraryDto(superCategoryDto, subCategoryDto, e.getServiceName(),
						e.getTypeOfService(), e.isServiceDecommisioned(), e.getServiceDescription(),
						e.getCreationDate(), e.getServiceId(), e.getLogoImage()))
				.collect(Collectors.toList());
		return target;
	}

}
