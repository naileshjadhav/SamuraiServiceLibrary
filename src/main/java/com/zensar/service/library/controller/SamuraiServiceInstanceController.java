package com.zensar.service.library.controller;

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

import com.zensar.service.library.model.SamuraiServiceInstanceDto;
import com.zensar.service.library.service.SamuraiServiceInstanceService;

@RestController
@CrossOrigin(origins = "*")
public class SamuraiServiceInstanceController {

	private static final Logger log = LoggerFactory.getLogger(SamuraiServiceInstanceController.class);
	@Autowired
	private SamuraiServiceInstanceService service;

	@PostMapping(value = "/instance")
	public ResponseEntity<SamuraiServiceInstanceDto> createSamuraiServiceInstance(
			@RequestBody SamuraiServiceInstanceDto dto) {
		log.info("Starting createSamuraiServiceInstance....");
		dto = this.service.createServiceInstance(dto);
		log.info("End createSamuraiServiceInstance....");
		return new ResponseEntity<SamuraiServiceInstanceDto>(dto, HttpStatus.CREATED);
	}

	@PutMapping(value = "/instance")
	public ResponseEntity<SamuraiServiceInstanceDto> updateSamuraiServiceInstance(
			@RequestBody SamuraiServiceInstanceDto dto) {
		log.info("Starting updateSamuraiServiceInstance....");
		dto = this.service.updateServiceInstance(dto);
		log.info("End updateSamuraiServiceInstance....");
		return new ResponseEntity<SamuraiServiceInstanceDto>(dto, HttpStatus.OK);
	}

	@GetMapping(value = "instance/{name}")
	public ResponseEntity<SamuraiServiceInstanceDto> getServiceInstanceByName(@PathVariable String name) {
		log.info("Starting getServiceInstanceByName....");
		SamuraiServiceInstanceDto dto = this.service.getServiceInstanceByName(name);
		log.info("Starting getServiceInstanceByName....");
		return new ResponseEntity<SamuraiServiceInstanceDto>(dto, HttpStatus.OK);
	}

}
