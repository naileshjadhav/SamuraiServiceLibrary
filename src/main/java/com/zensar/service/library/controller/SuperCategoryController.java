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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.service.library.model.SuperCategoryDto;
import com.zensar.service.library.service.SuperCategoryService;

@RestController
@CrossOrigin(origins = "*")
public class SuperCategoryController {

	@Autowired
	private SuperCategoryService superCategoryService;
	private static final Logger log = LoggerFactory.getLogger(SuperCategoryController.class);

	@GetMapping("/category/{id}")
	public ResponseEntity<SuperCategoryDto> getSupercategoryById(@PathVariable Long id) {
		log.info("Get category by id::" + id);
		SuperCategoryDto dto = superCategoryService.getSuperCategoryById(id);
		return new ResponseEntity<SuperCategoryDto>(dto, HttpStatus.OK);
	}

	@GetMapping("/categoryOne")
	public ResponseEntity<SuperCategoryDto> getDefaultSupercategoryOneById() {
		SuperCategoryDto dto = superCategoryService.getSuperCategoryById(1L);
		return new ResponseEntity<SuperCategoryDto>(dto, HttpStatus.OK);
	}

	@GetMapping("/categoryTwo")
	public ResponseEntity<SuperCategoryDto> getDefaultSupercategoryTwoById() {
		SuperCategoryDto dto = superCategoryService.getSuperCategoryById(2L);
		return new ResponseEntity<SuperCategoryDto>(dto, HttpStatus.OK);
	}

	@GetMapping("/category")
	public ResponseEntity<List<SuperCategoryDto>> getAllSupercategory() {
		log.info("Get all category::");
		List<SuperCategoryDto> dto = superCategoryService.getAllSuperCategory();
		return new ResponseEntity<List<SuperCategoryDto>>(dto, HttpStatus.OK);
	}

	@PostMapping("/category")
	public ResponseEntity<SuperCategoryDto> saveSuperCategory(@RequestBody SuperCategoryDto dto) {
		log.info("Save super category::" + dto.getSuperCategoryName());
		dto.setCreationDate(LocalDateTime.now());
		dto = superCategoryService.saveSuperCategory(dto);
		return new ResponseEntity<SuperCategoryDto>(dto, HttpStatus.OK);
	}
	
	@PutMapping("/category")
	public ResponseEntity<SuperCategoryDto> updateSuperCategory(@RequestBody SuperCategoryDto dto) {
		log.info("Save super category::" + dto.getSuperCategoryName());
		dto = superCategoryService.updateSuperCategory(dto);
		return new ResponseEntity<SuperCategoryDto>(dto, HttpStatus.OK);
	}
}
