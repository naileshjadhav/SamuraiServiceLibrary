package com.zensar.service.library.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zensar.service.library.model.ServiceLibraryDto;
import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.service.LibraryService;
import com.zensar.service.library.service.SubCategoryService;
import com.zensar.service.library.service.SuperCategoryService;

@RestController
@CrossOrigin(origins = "*")
public class ServiceLibraryController {

	@Autowired
	private LibraryService libraryService;
	@Autowired
	private SuperCategoryService superCategoryService;
	@Autowired
	private SubCategoryService subCategoryService;
	private static final Logger log = LoggerFactory.getLogger(ServiceLibraryController.class);

	@GetMapping(value = "/library/{id}", name = "libraryById", path = "/library/{id}")
	public ResponseEntity<ServiceLibraryDto> getServiceLibraryById(@PathVariable Long id) {
		log.info("Get service library by id: " + id);
		ServiceLibraryDto dto = libraryService.getServiceLibraryById(id);
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "/service/{name}")
	public ResponseEntity<List<ServiceLibraryDto>> getEnabledServiceLibraryByName(@PathVariable String name) {
		log.info("Get list service library by name: " + name);
		List<ServiceLibraryDto> dtos = libraryService.getEnabledServiceListByName(name);
		return new ResponseEntity<List<ServiceLibraryDto>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/service")
	public ResponseEntity<List<ServiceLibraryDto>> getEnabledServiceLibraryBySubCategoryId(
			@RequestParam(value = "id") Long id) {
		log.info("Get service library by subcategory id: " + id);
		List<ServiceLibraryDto> dtos = libraryService.getEnabledServicesBySubCategoryId(id);
		return new ResponseEntity<List<ServiceLibraryDto>>(dtos, HttpStatus.OK);
	}

	@GetMapping("/library")
	public ResponseEntity<List<ServiceLibraryDto>> getAllEnabledServiceLibrary() {
		log.info("Get full service library::");
		List<ServiceLibraryDto> dto = libraryService.getAllEnabledServiceLibrary();
		return new ResponseEntity<List<ServiceLibraryDto>>(dto, HttpStatus.OK);
	}

	@PostMapping(value = "/library", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ServiceLibraryDto> createServiceLibrary(@RequestParam(value = "logoImage") MultipartFile image,
			@RequestParam(value = "serviceDescription") String serviceDescription,
			@RequestParam(value = "superCategoryName") String superCategoryName,
			@RequestParam(value = "subCategoryName") String subCategoryName,
			@RequestParam(value = "serviceName") String serviceName,
			@RequestParam(value = "typeOfService") String typeOfService) throws IOException {
		log.info("Saving service library.....");
		ServiceLibraryDto dto = new ServiceLibraryDto();
		dto.setServiceDescription(serviceDescription);
		dto.setServiceName(serviceName);
		log.info("subCategoryName:: " + subCategoryName);
		SubCategoryDto subCategoryDto = subCategoryService.findSubCategoryByName(subCategoryName);
		log.info("subCategoryId:: " + subCategoryDto.getSubCategoryId());
		dto.setSubCategory(subCategoryDto);
		log.info("superCategoryName:: " + superCategoryName);
		SuperCategoryDto superCategoryDto = superCategoryService.findSuperCategoryByName(superCategoryName);
		log.info("superCategoryId:: " + subCategoryDto.getSubCategoryId());
		dto.setSuperCategory(superCategoryDto);
		dto.setTypeOfService(typeOfService);
		dto.setLogoImage(image.getBytes());
		dto = libraryService.saveServiceLibrary(dto);
		dto.setLogoImage(dto.getLogoImage());
		log.info("Save service library finished...");
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

	@PutMapping(value = "/library")
	public ResponseEntity<ServiceLibraryDto> updateService(@RequestBody ServiceLibraryDto dto) throws IOException {
		log.info("Updating Service..");
		dto = libraryService.updateService(dto);
		log.info("Updating Service finished..");
		return new ResponseEntity<ServiceLibraryDto>(dto, HttpStatus.OK);
	}

}
