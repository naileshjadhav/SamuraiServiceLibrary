package com.zensar.service.library.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.service.library.model.SubCategoryDto;
import com.zensar.service.library.service.SubCategoryService;

@RestController
@CrossOrigin(origins = "*")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;
	private static final Logger log = LoggerFactory.getLogger(SubCategoryController.class);

	@GetMapping("/subcategory/{id}")
	public ResponseEntity<SubCategoryDto> getServiceLibraryById(@PathVariable Long id) {
		log.info("Get sub category library by id::" + id);
		SubCategoryDto dto = subCategoryService.getSubcategoryById(id);
		return new ResponseEntity<SubCategoryDto>(dto, HttpStatus.OK);
	}
	
	@GetMapping("/subcategory")
	public ResponseEntity<List<SubCategoryDto>> getAllSubcategory() {
		log.info("Get all subcategory::");
		List<SubCategoryDto> dto = subCategoryService.getAllSubCategory();
		return new ResponseEntity<List<SubCategoryDto>>(dto, HttpStatus.OK);
	}

	@PostMapping("/subcategory")
	public ResponseEntity<SubCategoryDto> saveSubCategory(@RequestBody SubCategoryDto dto) {
		log.info("Save sub category ::" + dto.getSubCategoryName());
		dto.setCreationDate(LocalDateTime.now());
		dto = subCategoryService.saveSubCategory(dto);
		return new ResponseEntity<SubCategoryDto>(dto, HttpStatus.OK);
	}
}
